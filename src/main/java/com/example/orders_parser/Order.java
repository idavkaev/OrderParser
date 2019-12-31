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

    private int line;
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

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
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
                        "\"comment\":%s, " +
                        "\"filename\":%s, " +
                        "\"line\":%d, " +
                        "\"result\":%s }",
                getId(),
                getAmount(),
                getComment(),
                getFileName(),
                getLine(),
                isResult() ? "OK" : getErrMessage()

                );
    }

    public Order(){

    }

    public Order (String exception, int line, String fileName, boolean result) {
        this.errMessage = exception;
        this.line = line;
        this.fileName = fileName;
        this.result = result;
    }

}
