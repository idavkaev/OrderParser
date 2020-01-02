package com.example.orders_parser;

public class Line {
    public int getLineNumber() {
        return lineNumber;
    }

    public Line(int lineNumber, String line) {
        this.lineNumber = lineNumber;
        this.line = line;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    private int lineNumber;
    private String line;


}
