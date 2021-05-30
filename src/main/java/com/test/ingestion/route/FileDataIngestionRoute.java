package com.test.ingestion.route;

import com.test.ingestion.config.AppProperties;
import com.test.ingestion.config.KafkaProperties;
import com.test.ingestion.service.InternalIdService;
import com.test.ingestion.utils.Constants;
import com.test.ingestion.workflow.*;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileDataIngestionRoute extends SpringRouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(FileDataIngestionRoute.class);

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private InternalIdService internalIdService;

    @Override
    public void configure() throws Exception {

        from(Constants.ROUTE_DIRECT_ROUTING).process(new ExecutorProcessor(appProperties));
        from(Constants.ROUTE_DIRECT_ERROR).process(new ErrorHandlingProcessor());

//        String topicName = "brokers="+kafkaProperties.getKafkaBootstrapServer();
//        String kafkaServer = "kafka:"+kafkaProperties.getKafkaTopic();
//        String zooKeeperHost = "zookeeperHost="+kafkaProperties.getKafkaZookeeper()+"&zookeeperPort=2181";
//        String serializerClass = "org.apache.kafka.common.serialization.StringDeserializer";
//        String toKafka = new StringBuilder().append(kafkaServer).append("?").append(topicName)
//                .toString();

//
//        from(getKafkaConsumerEndpoint()).routeId(this.getClass().getName())
//                .log("on the partition ${headers[kafka.PARTITION]}")
//                .log("Message received from Kafka : ${body}")
//                .setExchangePattern(ExchangePattern.InOnly).end();
//
        from("direct:blmberg").process(new StartWorkflow(Constants.BLMBERG, "blmberg"))
                .to(Constants.ROUTE_DIRECT_ROUTING);

        from("direct:blmbergTransform").routeId("blmbergTransform").doTry()
                .process(new BlmbergDataTransform(internalIdService, Constants.BLMBERG, "blmbergTransform")).to(Constants.ROUTE_DIRECT_ROUTING)
                .doCatch(Exception.class).to(Constants.ROUTE_DIRECT_ERROR);

//        from("direct:blmbergStore").routeId("blmbergStore").doTry()
//                .process(new BlmbergDataStore(Constants.BLMBERG, "blmbergStore",securityRepository)).to(Constants.ROUTE_DIRECT_ROUTING)
//                .doCatch(Exception.class).to(Constants.ROUTE_DIRECT_ERROR);

        from("direct:endBlmbergProcess").process(new EndWorkflow(Constants.BLMBERG, Constants.OK_STR_201_CREATED,
                Constants.PROCESS_SUCCESS));

    }

//    public static class kafkaOutputBean {
//        public void printKafkaBody(String body) {
//            LOG.info("KafkaBody result >>>>> " + body);
//        }
//    }
//
//    public String getKafkaConsumerEndpoint() {
//        return  "kafka:" + kafkaProperties.getKafkaTopic()+ "?brokers=" +kafkaProperties.getKafkaBootstrapServer()  + "&groupId="
//                + "fileDataIngestionConsumer" + "&autoOffsetReset=earliest&pollTimeoutMs=60000"
//                + "&autoCommitEnable=true" + "&keyDeserializer=org.apache.kafka.common.serialization.StringDeserializer"
//                + "&valueDeserializer=org.apache.kafka.common.serialization.StringDeserializer";
//    }
}
