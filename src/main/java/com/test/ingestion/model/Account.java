package com.test.ingestion.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@JsonInclude(content = JsonInclude.Include.NON_NULL, value = JsonInclude.Include.NON_NULL)
public class Account implements Serializable {

    @JsonProperty("accountId")
    public String identification;

    @JsonProperty("number")
    public String number;

    @JsonProperty("name")
    public String name;

    @JsonProperty("accountId")
    public String getIdentification() {
        return identification;
    }

    @JsonProperty("accountId")
    public void setIdentification(String identification) {
        this.identification = identification;
    }

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
}
