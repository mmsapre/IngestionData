package com.test.ingestion.service;

import com.bazaarvoice.jolt.JsonUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.test.ingestion.model.Record;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.mongodb.BasicDBObjectBuilder.start;

@Component
public class CustomMongoRecordWriter implements ItemWriter<Record>, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(CustomMongoRecordWriter.class);

    @Autowired
    private DB db;

    @Override
    public void write(List<? extends Record> records) throws Exception {
        logger.info("Record fields");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        for (Record record : records) {


            String payload = objectMapper.writeValueAsString(record.getPayload());
            db.getCollection("records_temporary").save(start()
                    .add("fileId", record.getFileId())
                    .add("payload", payload).get());
        }

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        db.getCollection("records_temporary").ensureIndex(BasicDBObjectBuilder.start().add("fileId", 1).get());
    }
}
