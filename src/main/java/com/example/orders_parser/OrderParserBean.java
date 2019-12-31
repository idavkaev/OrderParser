package com.example.orders_parser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

@Component
public class OrderParserBean implements OrderParser {
    @Override
    public Order parse(String line, Extensions ext) throws JsonProcessingException {
        switch(ext) {
            case CSV:
                return parseCsv(line);

            case JSON:
                return parseJson(line);

            default:
                return null ;
        }
    }

    private Order parseJson(String line) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Order order = objectMapper.readValue(line, Order.class);
            return order;
        } catch (JsonProcessingException ext) {
            throw ext;
        }
    }

    private Order parseCsv(String line) throws JsonProcessingException {
        CsvSchema orderSchema = CsvSchema.builder()
                .addColumn("orderId")
                .addColumn("amount")
                .addColumn("currency")
                .addColumn("comment")
                .build();
        CsvMapper mapper = new CsvMapper();
        try {
            Order order = mapper.readerFor(Order.class).with(orderSchema).readValue(line);
            return order;
        } catch (JsonProcessingException  ext) {
            throw ext;
        }
    }
}
