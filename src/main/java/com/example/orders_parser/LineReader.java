package com.example.orders_parser;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class LineReader implements ItemReader<String> {

    private static final Logger log = LoggerFactory.getLogger(LineReader.class);
    private String fileName;
    private BufferedReader reader;

    public LineReader(String fileName) {
        this.fileName = fileName;
        this.reader = getFileReader();
    }

    private BufferedReader getFileReader() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            return reader;
        } catch (Exception e){
            return null;
        }

    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        log.info("Reading Line");
        String line = reader.readLine();
        System.out.println("AAAAAAAAAA " + line);
        return line;
    }
}
