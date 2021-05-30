package com.test.ingestion.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
//@EnableKafka
@Slf4j
public class KafkaConfig {
    @Value("${Kafka.BootstrapServers}")
    private String bootstrapServers;

    @Value("${Kafka.config.offsetPartition}")
    private String offsetTopicPartition;

    /**
     * Auto Offset Reset Config.
     */
    @Value("${Kafka.config.autooffsetreset}")
    private String autoOffsetReset;

    /**
     * Consumer group id.
     */
    @Value("${kafka.consumer.group-id}")
    private String groupId;

    @Value("${kafka.topic}")
    private String topic;

    /**
     * Dead letter queue topic.
     */
    @Value("${kafka.dlq.topic}")
    private String kafkaDlqTopic;


    private KafkaProperties kafkaProperties;

    @Autowired
    public KafkaConfig(KafkaProperties kafkaProps) {
        this.kafkaProperties = kafkaProps;
    }


    public Map<TopicPartition, Long> getOffsetPartionMap() {
        Map<TopicPartition, Long> topicPartitionLongMap = new HashMap<>();
        if (StringUtils.isNotEmpty(offsetTopicPartition)) {
            String topicMap[] = offsetTopicPartition.split(":");
            for (String topics : topicMap) {
                String topicPart[] = topics.split("\\|");
                TopicPartition topicPartition = new TopicPartition(this.topic, Integer.parseInt(topicPart[0]));
                topicPartitionLongMap.put(topicPartition, Long.parseLong(topicPart[1]));
            }
        }
        return topicPartitionLongMap;
    }


    public DefaultKafkaConsumerFactory<String, String> consumerFactory() {
        DefaultKafkaConsumerFactory defaultKafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(consumerConfigs());
        Map<String, Object> configMap = defaultKafkaConsumerFactory.getConfigurationProperties();
        configMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        log.info("****************** Properties with kafka consumer ********* ");
        configMap.forEach((k, v) -> {
            log.info(k + " :: " + v);
        });
        log.info("****************** End Properties with kafka consumer ********* ");
        return defaultKafkaConsumerFactory;
    }


    public Map<String, Object> consumerConfigs() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        log.info("Kafka s servers {}", bootstrapServers);
        log.info("Kafka Auto offset {}", autoOffsetReset);
        log.info("Kafka Output topic {}", topic);
        return properties;
    }


}
