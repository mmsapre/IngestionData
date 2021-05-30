package com.test.ingestion.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeedKafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.dlq.topic}")
    private String feedDlqTopic;

    @Value("${kafka.producer.topic}")
    private String feedProducerTopic;

    public void sendToDLQ(final ConsumerRecord<String, String> data, String errorMessage) {
        // Send to dead letter queue
        final ProducerRecord<String, String> producerRecord = getProducerRecord(data,errorMessage);
        producerRecord.headers().add("error_msg",errorMessage.getBytes()).add("error", errorMessage.getBytes());
        log.info("Routing to Dlq with error {}",errorMessage);
        kafkaTemplate.send(producerRecord);
    }

    private ProducerRecord<String, String> getProducerRecord(final ConsumerRecord<String, String> data ,String errorMessage) {
        return new ProducerRecord<>(feedDlqTopic, data.toString());
    }

}
