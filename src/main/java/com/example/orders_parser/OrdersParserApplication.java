package com.example.orders_parser;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class OrdersParserApplication implements CommandLineRunner {

    @Autowired
    OrderParserBean orderParserBean;

    public static void main(String[] args) {
        SpringApplication.run(OrdersParserApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (String i : args) {
            System.out.println(i);
            ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

            try (BufferedReader reader = new BufferedReader(new FileReader(i))) {
                int lineCounter = 0;
                String line = reader.readLine();

                while (line != null) {
                    lineCounter ++;

                    String extension = FilenameUtils.getExtension(i);
                    Order order;
                    if (EnumUtils.isValidEnumIgnoreCase(Extensions.class, extension)) {
                        try {
                            order = orderParserBean.parse(line, EnumUtils.getEnumIgnoreCase(Extensions.class, extension));
                            order.setFileName(i);

                        } catch(JsonMappingException | JsonParseException e) {
                            order = new Order(e.getLocalizedMessage(), lineCounter, i, false);
                        }
                        System.out.println(order);
                    }
                    line = reader.readLine();
                }

                } catch(Exception e){
                    throw e;
                }
        }
    }
}
