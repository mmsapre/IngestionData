package com.test.ingestion.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BloombergDTO implements Serializable {

    @Id
    @JsonProperty("internalId")
    private String internalId;
    @JsonProperty("providerId")
    private String providerId;
    @JsonProperty("mktValue")
    private double mktValue;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("fileId")
    private String fileId;
    @JsonProperty("provider")
    private String provider;
    @JsonProperty("open")
    private double open;
    @JsonProperty("last")
    private double last;
    @JsonProperty("low")
    private double low;
    @JsonProperty("close")
    private double close;
    @JsonProperty("turnover")
    private double turnover;
    @JsonProperty("totalquantity")
    private int totalquantity;
    @JsonProperty("date")
    private String date;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 5037801540756149718L;

    /**
     * No args constructor for use in serialization
     *
     */
    public BloombergDTO() {
    }

    /**
     *
     * @param date
     * @param symbol
     * @param last
     * @param mktValue
     * @param totalquantity
     * @param internalId
     * @param provider
     * @param low
     * @param providerId
     * @param close
     * @param turnover
     * @param open
     * @param fileId
     */
    public BloombergDTO(String internalId, String providerId, double mktValue, String symbol, String fileId, String provider, double open, double last, double low, double close, double turnover, int totalquantity, String date) {
        super();
        this.internalId = internalId;
        this.providerId = providerId;
        this.mktValue = mktValue;
        this.symbol = symbol;
        this.fileId = fileId;
        this.provider = provider;
        this.open = open;
        this.last = last;
        this.low = low;
        this.close = close;
        this.turnover = turnover;
        this.totalquantity = totalquantity;
        this.date = date;
    }

    @JsonProperty("internalId")
    public String getInternalId() {
        return internalId;
    }

    @JsonProperty("internalId")
    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    @JsonProperty("providerId")
    public String getProviderId() {
        return providerId;
    }

    @JsonProperty("providerId")
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @JsonProperty("mktValue")
    public double getMktValue() {
        return mktValue;
    }

    @JsonProperty("mktValue")
    public void setMktValue(double mktValue) {
        this.mktValue = mktValue;
    }

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("fileId")
    public String getFileId() {
        return fileId;
    }

    @JsonProperty("fileId")
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @JsonProperty("provider")
    public String getProvider() {
        return provider;
    }

    @JsonProperty("provider")
    public void setProvider(String provider) {
        this.provider = provider;
    }

    @JsonProperty("open")
    public double getOpen() {
        return open;
    }

    @JsonProperty("open")
    public void setOpen(double open) {
        this.open = open;
    }

    @JsonProperty("last")
    public double getLast() {
        return last;
    }

    @JsonProperty("last")
    public void setLast(double last) {
        this.last = last;
    }

    @JsonProperty("low")
    public double getLow() {
        return low;
    }

    @JsonProperty("low")
    public void setLow(double low) {
        this.low = low;
    }

    @JsonProperty("close")
    public double getClose() {
        return close;
    }

    @JsonProperty("close")
    public void setClose(double close) {
        this.close = close;
    }

    @JsonProperty("turnover")
    public double getTurnover() {
        return turnover;
    }

    @JsonProperty("turnover")
    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    @JsonProperty("totalquantity")
    public int getTotalquantity() {
        return totalquantity;
    }

    @JsonProperty("totalquantity")
    public void setTotalquantity(int totalquantity) {
        this.totalquantity = totalquantity;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BloombergDTO.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("internalId");
        sb.append('=');
        sb.append(((this.internalId == null)?"<null>":this.internalId));
        sb.append(',');
        sb.append("providerId");
        sb.append('=');
        sb.append(((this.providerId == null)?"<null>":this.providerId));
        sb.append(',');
        sb.append("mktValue");
        sb.append('=');
        sb.append(this.mktValue);
        sb.append(',');
        sb.append("symbol");
        sb.append('=');
        sb.append(((this.symbol == null)?"<null>":this.symbol));
        sb.append(',');
        sb.append("fileId");
        sb.append('=');
        sb.append(((this.fileId == null)?"<null>":this.fileId));
        sb.append(',');
        sb.append("provider");
        sb.append('=');
        sb.append(((this.provider == null)?"<null>":this.provider));
        sb.append(',');
        sb.append("open");
        sb.append('=');
        sb.append(this.open);
        sb.append(',');
        sb.append("last");
        sb.append('=');
        sb.append(this.last);
        sb.append(',');
        sb.append("low");
        sb.append('=');
        sb.append(this.low);
        sb.append(',');
        sb.append("close");
        sb.append('=');
        sb.append(this.close);
        sb.append(',');
        sb.append("turnover");
        sb.append('=');
        sb.append(this.turnover);
        sb.append(',');
        sb.append("totalquantity");
        sb.append('=');
        sb.append(this.totalquantity);
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
