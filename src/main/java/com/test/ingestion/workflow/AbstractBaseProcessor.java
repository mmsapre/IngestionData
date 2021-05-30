package com.test.ingestion.workflow;

import com.test.ingestion.utils.Constants;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractBaseProcessor implements Processor{

    public abstract void process(Exchange exchange) throws IOException, SAXException; //NOPMD ExplicitException

    public void setCurrentStepAndResult(final Exchange exchange, final String workflowName, final String step, final String result) { //NOPMD explicitException
        Workflow wkc = (Workflow) exchange.getIn().getHeader(Constants.CONTEXT);
        final Map<?, ?> hdrMap=(Map<?, ?>)exchange.getIn().getHeaders().get(Constants.HEADER);
        if (wkc == null) {
            wkc = new Workflow();
        }
        wkc.setCurrentStep(step);
        wkc.setResult(result);
        wkc.setWorkflowName(workflowName);
        exchange.getOut().setHeader(Constants.CONTEXT, wkc);
        exchange.getOut().setHeader(Constants.HEADER,hdrMap);
        exchange.getOut().setBody(exchange.getIn().getBody());
    }
}
