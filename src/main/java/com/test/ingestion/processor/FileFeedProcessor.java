package com.test.ingestion.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.ingestion.model.DistributionEvent;
import com.test.ingestion.model.Record;
import com.test.ingestion.utils.Constants;
import org.apache.camel.ProducerTemplate;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@Component
public class FileFeedProcessor implements ItemProcessor<String, Record> {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Override
    public Record process(String feed) throws Exception {
        String templateId=null;
        DistributionEvent distributionEvent=null;
        Record response=null;

        ObjectMapper objectMapper=new ObjectMapper();
        try {
            distributionEvent=objectMapper.readValue(feed, DistributionEvent.class);
            templateId=distributionEvent.getHeaders().get("templateId");
            String endPointURI="direct:"+templateId.toLowerCase();
            final Map<String, Object> headers = new HashMap<>();
            response=producerTemplate.requestBodyAndHeader(endPointURI,feed, Constants.HEADER, headers,Record.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }
}
