package com.test.ingestion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.Map;

public class Record {
    @Id
    private long id;
    @JsonProperty("fileId")
    private String fileId;
    @JsonProperty("payload")
    private Map<String, String> payload = new HashMap<String, String>();


    @JsonProperty("fileId")
    public String getFileId() {
        return fileId;
    }

    @JsonProperty("fileId")
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @JsonProperty("payload")
    public Map<String, String> getPayload() {
        return payload;
    }

    @JsonProperty("payload")
    public void setPayload(Map<String, String> payload) {
        this.payload = payload;
    }
}
