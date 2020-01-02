package com.example.orders_parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"orderId", "amount", "currency", "comment"})
@JsonIgnoreProperties({"line", "result", "filename"})
public class Order {

    private double amount;
    private String currency;
    @JsonProperty("orderId")
    private int id;
    private String comment;

    private int lineNumber;
    private boolean result = true;
    private String fileName;
    private String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return String.format("{" +
                        "\"id\":%d, " +
                        "\"amount\":%f, " +
                        "\"currency:%s\", " +
                        "\"comment\":%s, " +
                        "\"filename\":%s, " +
                        "\"line\":%d, " +
                        "\"result\":%s }",
                getId(),
                getAmount(),
                getCurrency(),
                getComment(),
                getFileName(),
                getLineNumber(),
                isResult() ? "OK" : getErrMessage()

                );
    }

    public Order(){

    }

    public Order (String exception, int lineNumber, String fileName, boolean result) {
        this.errMessage = exception;
        this.lineNumber = lineNumber;
        this.fileName = fileName;
        this.result = result;
    }

}
