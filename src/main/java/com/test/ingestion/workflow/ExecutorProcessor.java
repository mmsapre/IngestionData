package com.test.ingestion.workflow;

import com.test.ingestion.config.AppProperties;
import com.test.ingestion.utils.Constants;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ExecutorProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorProcessor.class);

    private AppProperties appProperties;

    public ExecutorProcessor(final AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public AppProperties getAppProperties() {
        return appProperties;
    }

    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    private String getNextStep(final Exchange exchange, final String workflowName, final String result) {
        final Workflow workflow = (Workflow) exchange.getIn().getHeader(Constants.CONTEXT);
        final String currentStep = workflow.getCurrentStep();
        // System.out.println("(currentStep + \"-\" + result)");
        return appProperties.getWorkflow().getProcessor().get(workflowName).get(currentStep + "-" + result);
    }

    @Override
    public void process(final Exchange exchange) {
        final CamelContext context = exchange.getContext();
        final Message message = exchange.getIn();
        final Map<?, ?> hdrMap = (Map<?, ?>) exchange.getIn().getHeaders().get(Constants.HEADER);
        final Object object = message.getBody();
        LOGGER.debug("in executor name: {}", object);
        final Workflow workflow = (Workflow) exchange.getIn().getHeader(Constants.CONTEXT);
        final String result = workflow.getResult();
        final String workflowName = workflow.getWorkflowName();
        exchange.getOut().setBody(object);
        exchange.getOut().setHeader(Constants.HEADER, hdrMap);
        final String nextStep = getNextStep(exchange, workflowName, result);
        exchange.getOut().setHeader(Constants.HEADER, hdrMap);
        context.createProducerTemplate().send("direct:" + nextStep, exchange);
    }

}
