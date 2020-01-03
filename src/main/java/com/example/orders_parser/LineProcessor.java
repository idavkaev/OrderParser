package com.example.orders_parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class LineProcessor implements ItemProcessor<Line, Order> {

    private static final Logger log = LoggerFactory.getLogger(LineProcessor.class);

    private String fileName;

    public LineProcessor(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Order process(Line s) throws Exception {
        log.info("Processing line");
        String extension = FilenameUtils.getExtension(fileName);
        return parse(s, EnumUtils.getEnumIgnoreCase(Extensions.class, extension));
    }

    public Order parse(Line line, Extensions ext) throws JsonProcessingException {
        switch(ext) {
            case CSV:
                return parseCsv(line);

            case JSON:
                return parseJson(line);

            case XLSX:
            default:
                return null ;
        }
    }

    private Order parseJson(Line line) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Order order = mapper.readValue(line.getLine(), Order.class);
            order.setFileName(FilenameUtils.getName(fileName));
            order.setLineNumber(line.getLineNumber());
            return order;
        } catch (JsonProcessingException ext) {
            Order order = new Order();
            order.setFileName(FilenameUtils.getName(fileName));
            order.setLineNumber(line.getLineNumber());
//            order.setResult(false);
            order.setResult(ext.getMessage());
            return order;
        }
    }

    private Order parseCsv(Line line) {
        CsvSchema orderSchema = CsvSchema.builder()
                .addColumn("orderId")
                .addColumn("amount")
                .addColumn("currency")
                .addColumn("comment")
                .build();
        CsvMapper mapper = new CsvMapper();
        try {
            Order order = mapper.readerFor(Order.class).with(orderSchema).readValue(line.getLine());
            order.setFileName(FilenameUtils.getName(fileName));
            order.setLineNumber(line.getLineNumber());
            return order;
        } catch (JsonProcessingException ext) {
            Order order = new Order();
            order.setFileName(FilenameUtils.getName(fileName));
            order.setLineNumber(line.getLineNumber());
//            order.setResult(false);
            order.setResult(ext.getMessage());
            return order;
        }
    }

//   TODO: Implement to support XLSX
//    private Order parseXlsx() {
//        return null;
//    }
}
