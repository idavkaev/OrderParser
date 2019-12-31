package com.example.orders_parser;

public enum Extensions {
    CSV("csv"),
    JSON("json"),
    XLSX("xlsx");

    public final String label;
    private Extensions(String label){
        this.label = label;
    }


}
