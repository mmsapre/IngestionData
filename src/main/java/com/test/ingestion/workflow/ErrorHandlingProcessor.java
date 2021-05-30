package com.test.ingestion.workflow;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Slf4j
public class ErrorHandlingProcessor implements Processor {


    @Override
    public void process(final Exchange exchange) throws Exception {


        final Object obj = exchange.getProperty(Exchange.EXCEPTION_CAUGHT);

        log.warn("Error handler :: {}", obj);
        exchange.getIn().setHeader("error", obj);

        exchange.getIn().setBody("");
    }
}
