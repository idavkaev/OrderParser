package com.example.orders_parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleWriter implements ItemWriter<Order> {
    private static final Logger log = LoggerFactory.getLogger(ConsoleWriter.class);

    @Override
    public void write(List<? extends Order> list) throws Exception {
        log.info("Writing");
        for (Order order : list) {
            System.out.println(order.toString());
        }
    }
}
