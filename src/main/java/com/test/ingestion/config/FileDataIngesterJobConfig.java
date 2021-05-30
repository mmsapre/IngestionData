package com.test.ingestion.config;

import com.test.ingestion.model.Record;
import com.test.ingestion.processor.FileFeedProcessor;
import com.test.ingestion.service.CustomMongoRecordWriter;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.time.Instant;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableBatchProcessing
public class FileDataIngesterJobConfig {

    private static final Logger logger = LoggerFactory.getLogger(FileDataIngesterJobConfig.class);
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

//    @Autowired
//    private CustomKafkaItemWriter customKafkaItemWriter;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private KafkaConfig kafkaConfig;

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private CustomMongoRecordWriter customMongoRecordWriter;

    @Bean(name = "kafkaReader")
    public Job kafkaReader(Step step1){

      return jobBuilderFactory.get("filespooler-"+ Instant.now().toString())
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();

    }

    @Bean
    public KafkaItemReader<String, String> kafkaItemReader() {
        Properties props = new Properties();
        props.putAll(this.kafkaConfig.consumerConfigs());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        Map<TopicPartition,Long> offsetMap=this.kafkaConfig.getOffsetPartionMap();
        return new KafkaItemReaderBuilder<String, String>()
                .partitions(0)
                .partitionOffsets(offsetMap)
                .consumerProperties(props)
                .name("feedSpoolReader")
                .saveState(true)
                .topic(kafkaProperties.getKafkaTopic())
                .build();
    }
    @Bean
    public FileFeedProcessor fileFeedProcessor() {
        return new FileFeedProcessor();
    }

    @Bean
    public Step step1(KafkaItemReader kafkaItemReader,FileFeedProcessor fileFeedProcessor) throws Exception {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(100)
                .reader(kafkaItemReader)
                .processor(fileFeedProcessor)
                .writer(writer())
                .build();
    }


    public ItemWriter<Record> writer() {

        return this.customMongoRecordWriter;
    }

}
