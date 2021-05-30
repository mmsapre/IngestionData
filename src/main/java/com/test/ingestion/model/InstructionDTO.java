package com.test.ingestion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class InstructionDTO implements Serializable {

    @JsonProperty("txnId")
    private String txnId;
    @JsonProperty("fileId")
    private String fileId;
    @JsonProperty("creditor")
    private Account creditAccount;
    @JsonProperty("debitor")
    private Account debitAccount;
    @JsonProperty("customer")
    public Customer customer;
    @JsonProperty("txnAmount")
    public Amount txnAmount;
    @JsonProperty("charges")
    public Amount charges;
    @JsonProperty("total")
    public Amount totalAmount;
    @JsonProperty("executionDate")
    private String executionDate;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("email")
    private String contactEmail;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("remark")
    private String remark;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 8750815413006045566L;

    /**
     * No args constructor for use in serialization
     *
     */
    public InstructionDTO() {
    }

    @JsonProperty("txnId")
    public String getTxnId() {
        return txnId;
    }

    @JsonProperty("txnId")
    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    @JsonProperty("fileId")
    public String getFileId() {
        return fileId;
    }

    @JsonProperty("fileId")
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @JsonProperty("creditor")
    public Account getCreditAccount() {
        return creditAccount;
    }

    @JsonProperty("creditor")
    public void setCreditAccount(Account creditAccount) {
        this.creditAccount = creditAccount;
    }

    @JsonProperty("debitor")
    public Account getDebitAccount() {
        return debitAccount;
    }

    @JsonProperty("debitor")
    public void setDebitAccount(Account debitAccount) {
        this.debitAccount = debitAccount;
    }

    @JsonProperty("customer")
    public Customer getCustomer() {
        return customer;
    }

    @JsonProperty("customer")
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @JsonProperty("txnAmount")
    public Amount getTxnAmount() {
        return txnAmount;
    }

    @JsonProperty("txnAmount")
    public void setTxnAmount(Amount txnAmount) {
        this.txnAmount = txnAmount;
    }

    @JsonProperty("charges")
    public Amount getCharges() {
        return charges;
    }

    @JsonProperty("charges")
    public void setCharges(Amount charges) {
        this.charges = charges;
    }

    @JsonProperty("total")
    public Amount getTotalAmount() {
        return totalAmount;
    }

    @JsonProperty("total")
    public void setTotalAmount(Amount totalAmount) {
        this.totalAmount = totalAmount;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("email")
    public String getContactEmail() {
        return contactEmail;
    }

    @JsonProperty("email")
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("remark")
    public String getRemark() {
        return remark;
    }

    @JsonProperty("remark")
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonProperty("executionDate")
    public String getExecutionDate() {
        return executionDate;
    }

    @JsonProperty("executionDate")
    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }
}
