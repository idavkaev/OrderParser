package com.example.orders_parser;

import org.springframework.batch.item.file.LineMapper;

import java.util.HashMap;
import java.util.Map;

public class LineNumberMapper implements LineMapper<Line> {
    @Override
    public Line mapLine(String s, int i) throws Exception {
        return new Line(i, s);

    }
}
