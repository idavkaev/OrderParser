package com.example.orders_parser;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonPropertyOrder({"id", "amount", "comment", "filename", "line", "result"})
//@JsonIgnoreProperties({"line", "result", "filename"})
public class Order {

    @JsonProperty("id")
    @JsonAlias("orderId")
    private int id;
    private double amount;
    @JsonIgnore
    private String currency;
    private String comment;
    @JsonProperty("filename")
    private String fileName;
    @JsonProperty("line")
    private int lineNumber;
    private String result = "OK";

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
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

//    @Override
//    public String toString() {
//        return String.format("{" +
//                        "\"id\":%d, " +
//                        "\"amount\":%f, " +
//                        "\"currency\":%s\", " +
//                        "\"comment\":%s, " +
//                        "\"filename\":%s, " +
//                        "\"line\":%d, " +
//                        "\"result\":%s }",
//                getId(),
//                getAmount(),
//                getCurrency(),
//                getComment(),
//                getFileName(),
//                getLineNumber(),
//                isResult() ? "OK" : getErrMessage()
//
//                );
//    }


    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "Could not serialize that instance";
        }
    }

    public Order(){

    }

//    public Order (String exception, int lineNumber, String fileName, boolean result) {
//        this.errMessage = exception;
//        this.lineNumber = lineNumber;
//        this.fileName = fileName;
//        this.result = result;
//    }

}
