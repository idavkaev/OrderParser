package com.example.orders_parser;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OrderParser {
    Order parse(String line, Extensions ext) throws JsonProcessingException;
}
