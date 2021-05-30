//package com.test.ingestion.consumer;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.test.ingestion.config.AppProperties;
//import com.test.ingestion.model.DistributionEvent;
//import com.test.ingestion.utils.Constants;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.camel.ProducerTemplate;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.common.header.Header;
//import org.apache.kafka.common.header.Headers;
//import org.springframework.kafka.listener.AcknowledgingMessageListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
////@Component
//@Slf4j
//public class DataIngestionKafkaConsumer implements AcknowledgingMessageListener<String, String> {
//
//    private ProducerTemplate producerTemplate;
//    private AppProperties appProperties;
//    public DataIngestionKafkaConsumer(ProducerTemplate producerTemplate, AppProperties appProperties) {
//        this.producerTemplate = producerTemplate;
//        this.appProperties=appProperties;
//    }
//
//
//    @Override
//    public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) {
//        log.info("Enter kafka listener");
//        String templateId=null;
//        DistributionEvent distributionEvent=null;
//        String record=data.value();
//        ObjectMapper objectMapper=new ObjectMapper();
//        try {
//            distributionEvent=objectMapper.readValue(record, DistributionEvent.class);
//            templateId=distributionEvent.getHeaders().get("templateId");
//            String endPointURI="direct:"+templateId.toLowerCase();
//            final Map<String, Object> headers = new HashMap<>();
//            producerTemplate.requestBodyAndHeader(endPointURI,record, Constants.HEADER, headers,String.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//
//
//    }
//}
