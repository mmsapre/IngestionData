# Ingestion Data Module

* [Summary](#summary)
* [Prerequisites](#prerequisites)
* [Configuratoin](#configuration)  
* [Build](#build)
* [High Level Overview](#high-level-overview)
* [Run](#deploy)
* [Author](#author)

[img-ingestdata]: img/IngestionData.jpeg

## Summary
+ Records received on kafka partitions are enriched and transformed before storing to Mongo based collection of records.
  In the collection record identified and transformed event is maintained for later retrieval.

## Prerequisites

To be able to run this module it is recommended to ensure following prerequisites are fulfilled:

+ Developer is aware of tools/technologies used here:
    - Core Java and basic MVC concept
    - Spring framework (Spring DI, Beans, REST) , Spring batch and Springboot
    - MongoDb
    - Apache Camel  
    - Kafka
    - Eclipse, Junit, Maven
+ Necessary access/installation in place:
    - Eclipse (Oxygen)
    - Maven (3.5.2+)

Ensure local installation of the following softwares/tools:

* JDK - 1.8
* MongoDb - 4.0.x
* Kafka 1.x 
* Maven - 3.6.x

---
## Build
Open your command/shell terminal and navigate to your project's root directory

#### Execute the following command to build the jar without running test cases:
mvn -U clean compile -DskipTests

## High Level Overview

This ingestion module to reads json data stored in kafka to MongoDb records collection.Payload received on kafka
consists of templateId based on which message is routed to camel based endpoint for enrichment -> transform -> store.
Ingestion module can be started on demand until all the data on topic are consumed.Every ingestion module will have 
template based enrichment and routing process defined as part of configuration provided at the start.
Job related data will be logged into below collections.

+ MongoDB Collections :
  - BatchJobInstance : Maintains different jobs executed.
  - BatchJobExecution : Contains jobs execution details for the instance.
  - BatchStepExecution : Maintains steps executed as part of job execution.
  - BatchJobParameter : Maintains job execution parameters.
  - Records : Maintains all the events with Id and transformed event.


Diagram below, shows high level component diagram for ingestion data script

![Ingestion Data Script][img-ingestdata]

  - Event read from kafka topic which is published by [file data ingestion](https://github.com/mmsapre/FileBatchIngestion).
```shell
{
  "headers": {
    "templateId": "BLMBERG"
  },
  "payload": {
    "HIGH": "293",
    "TURNOVER": "4157.17",
    "CLOSE": "285.25",
    "source": "BLMBERG",
    "ISIN": "INE306L01010",
    "NAME": "QHEAL",
    "OPEN": "292.95",
    "MVAL": "4157.17",
    "TOTALQUANTITY": "1446384",
    "DATE": "2018-05-10",
    "LAST": "284.15",
    "LOW": "283.45",
    "timestamp": "2021-05-26T09:49:26.004Z",
    "fileId": "blmberg-1622022563682-1"
  }
}
```

## Configuration
+ Kafka properties
````snakeyaml
Kafka:
  config:
    autooffsetreset: earliest // Offset
    offsetPartition: "0|2520" // Partition offset from which reader to poll "partition"|"offset"
  consumer:
    group-id: fileDataIngestion // Consumer group
  producer:
    topic: filespool-dataingest
  BootstrapServers: localhost:9092
  ZookeeperHost: localhost:2081
  topic: filespool-queue-6
  dlq:
    topic: dlq-filespool-queue // Dead letter queue

````
+ Distribution processing configuration properties
```snakeyaml
distribution:
  distributionTransform:
    BLMBERG: blmbergmapper.json // transform received json to format for enrichment.
  Workflow:
    processor:
      BLMBERG: // Routing slip to transform enrich
        blmberg-success: blmbergTransform 
        blmbergTransform-success: endBlmbergProcess
        blmbergStore-success: endBlmbergProcess
```
+ MongoDb configuration

```snakeyaml
spring:
  data:
    mongodb:
      #      uri: mongodb://myadmin:secret@localhost:27017/test?authSource=admin
      host: localhost
      port: 27017
      database: test
      username:
      password:
```
+ Redis confiuration properties
```snakeyaml
Redis:
  Cache:
    TTL: 60000
  DB:
    Url: localhost:6379
    Password:
    Instance: 0
    TimeOut: 10000
```
## Deploy

Clone locally
Execute standard maven deploy command to build and deploy library into Artifact repository.

```shell
# Build and deploy
mvn clean deploy
```

## Author

* Repo owner - maheshsapre@gmail.com
