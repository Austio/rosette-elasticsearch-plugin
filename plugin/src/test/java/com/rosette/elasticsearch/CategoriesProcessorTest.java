/*
* Copyright 2017 Basis Technology Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.rosette.elasticsearch;

import org.elasticsearch.ingest.IngestDocument;
import org.elasticsearch.ingest.RandomDocumentPicks;
import org.elasticsearch.test.ESSingleNodeTestCase;
import org.hamcrest.Matchers;

import java.util.HashMap;
import java.util.Map;

public class CategoriesProcessorTest extends ESSingleNodeTestCase {

    public void testCategories() throws Exception {
        CategoriesProcessor processor = new CategoriesProcessor(new RosetteApiWrapper(), randomUnicodeOfLength(10), "text", "category");

        String inputText = "The people played lots of sports like soccer and hockey. The score was very high. Touchdown!";

        Map<String, Object> document = new HashMap<>();
        document.put("text", inputText);
        IngestDocument ingestDocument = RandomDocumentPicks.randomIngestDocument(random(), document);
        processor.execute(ingestDocument);

        assertThat(ingestDocument.getSourceAndMetadata().get("text"), Matchers.equalTo(inputText));
        assertThat(ingestDocument.getSourceAndMetadata().get("category"), Matchers.equalTo("SPORTS"));
    }
}
