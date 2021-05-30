package com.test.ingestion.route;
import com.test.ingestion.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PayloadWorkflowMapper {

	private final static Logger LOGGER = LoggerFactory.getLogger(PayloadWorkflowMapper.class);

	@Autowired
	private AppProperties appProperties;



	private final Map<String, Map<String, String>> typeMapToWorkflow = new HashMap<>();

	public void process(final String templateKey) { // NOPMD reassigning documentType
		LOGGER.info("inside process of PayloadWorkflowMapper");

		final Map<String, Map<String, String>> typeMapToWorkflow = appProperties.getWorkflow().getProcessor();


	}

}
