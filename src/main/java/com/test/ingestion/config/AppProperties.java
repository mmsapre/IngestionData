package com.test.ingestion.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;


@ConfigurationProperties(prefix = "distribution", ignoreUnknownFields = true)
public class AppProperties {

	private final Workflow workflow = new Workflow();
	public Workflow getWorkflow() {
		return workflow;
	}


	public static class Workflow {

		private Map<String, String> distributionTransform = new HashMap<String,String>();
		private Map<String, Map<String, String>> processor = new HashMap<String, Map<String, String>>();

		public Map<String, Map<String, String>> getProcessor() {
			return processor;
		}

		public void setProcessor(final Map<String, Map<String, String>> processor) {
			this.processor = processor;
		}

		public Map<String, String> getDistributionTransform() {
			return distributionTransform;
		}

		public void setDistributionTransform(Map<String, String> distributionTransform) {
			this.distributionTransform = distributionTransform;
		}
	}


}
