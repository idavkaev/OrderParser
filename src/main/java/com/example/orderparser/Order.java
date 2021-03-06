package com.example.orderparser;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonPropertyOrder({"id", "amount", "comment", "filename", "line", "result"})
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

    public String getResult() { return result; }

    public void setResult(String result) {
        this.result = result;
    }

    public double getAmount() { return amount; }

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
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "Could not serialize instance of Order";
        }
    }

    public Order(){
    }

}
