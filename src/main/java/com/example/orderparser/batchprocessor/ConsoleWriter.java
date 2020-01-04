package com.example.orderparser.batchprocessor;

import com.example.orderparser.Order;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleWriter implements ItemWriter<Order> {

    @Override
    public void write(List<? extends Order> list) {
        for (Order order : list) {
            System.out.println(order.toString());
        }
    }
}
