package com.example.orderparser.batchprocessor;

import com.example.orderparser.Line;
import org.springframework.batch.item.file.LineMapper;

public class LineNumberMapper implements LineMapper<Line> {

    @Override
    public Line mapLine(String s, int i) {
        return new Line(i, s);
    }
}
