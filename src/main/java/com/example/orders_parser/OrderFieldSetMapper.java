package com.example.orders_parser;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class OrderFieldSetMapper implements FieldSetMapper {
    @Override
    public Order mapFieldSet(FieldSet fieldSet) throws BindException {
        Order order = new Order();
//        "orderId", "amount", "currency", "comment"
        order.setId(fieldSet.readInt("orderId"));
        order.setAmount(fieldSet.readInt("amount"));
        order.setCurrency(fieldSet.readString("currency"));
        order.setComment(fieldSet.readString("comment"));
        return order;
    }
}
