package com.test.ingestion.workflow;
import com.test.ingestion.utils.Constants;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartWorkflow extends AbstractBaseProcessor{

    private static final Logger LOGGER = LoggerFactory.getLogger(StartWorkflow.class);
    private final String workflowName;
    private final String startWf;
    
    public StartWorkflow(final String workflowName, final String startWf) {
    	super();
        this.workflowName=workflowName;
        this.startWf=startWf;
    }
    @Override
    public void process(final Exchange exchange)  {
		final Message message = exchange.getIn();
		LOGGER.debug("in start work processor: {} ", message.getBody());
        setCurrentStepAndResult(exchange,this.workflowName, startWf, Constants.SUCCESS);
    }
}
