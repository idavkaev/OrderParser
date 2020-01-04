package com.example.orderparser;

public class Line {

    private int lineNumber;
    private String line;

    public Line(int lineNumber, String line) {
        this.lineNumber = lineNumber;
        this.line = line;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

}
