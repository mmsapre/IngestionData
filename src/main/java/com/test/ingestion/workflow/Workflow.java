package com.test.ingestion.workflow;

public class Workflow {


    private String workflowName;

    private String currentStep;

    private String result;


    public String getWorkflowName() {
        return workflowName;
    }


    public void setWorkflowName(final String workflowName) {
        this.workflowName = workflowName;
    }

    public String getCurrentStep() {
        return currentStep;
    }


    public void setCurrentStep(final String currentStep) {
        this.currentStep = currentStep;
    }


    public String getResult() {
        return result;
    }


    public void setResult(final String result) {
        this.result = result;
    }
}
