package com.test.ingestion.workflow;

import com.test.ingestion.model.Record;
import com.test.ingestion.utils.Constants;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class EndWorkflow implements Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(EndWorkflow.class);
	private final String workflowName;
	private final String statusCode;
	private final String statusMsg;
	public EndWorkflow(final String workflowName, final String statusCode, final String statusMsg) {
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
		this.workflowName = workflowName;
	}
	@Override
	public void process(final Exchange exchange) throws Exception {
		LOGGER.debug("Completed processing workflow ...:::{}", workflowName);
		final Map<?, ?> hdrMap = (Map<?, ?>) exchange.getIn().getHeaders().get(Constants.HEADER);

		final String workflow = (String) hdrMap.get(Constants.WORKFLOW_TYPE);

		Record result= (Record) exchange.getIn().getHeader(Constants.RESULT);

		exchange.getOut().setBody(result);

	}
}
