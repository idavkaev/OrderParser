package com.example.orderparser.configuration;

import com.example.orderparser.*;
import com.example.orderparser.batchprocessor.ConsoleWriter;
import com.example.orderparser.batchprocessor.LineNumberMapper;
import com.example.orderparser.batchprocessor.LineProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
@PropertySource("application.properties")
public class OrderProcessorConfiguration {

    @Value("${maxThreads}")
    private Integer maxThreads;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(maxThreads);
        return taskExecutor;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Line> reader(@Value("#{jobParameters['file']}") String input) {
        FlatFileItemReader reader = new FlatFileItemReader<>();
        reader.setLineMapper(new LineNumberMapper());
        reader.setResource(new FileSystemResource(input));

        return reader;
    }

    @Bean()
    @StepScope
    public ItemProcessor<Line, Order> processor(@Value("#{jobParameters['file']}") String fileName) {
        return new LineProcessor(fileName);
    }

    @Bean
    public ItemWriter<Order> writer(){
        return new ConsoleWriter();
    }

    @Bean
    public Job job(Step step) {
        return jobBuilderFactory
                .get("OrderParserJob")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step step(ItemReader<Line> myLineReader, ItemProcessor<Line, Order> myLineProcessor, ItemWriter<Order> writer) {
        return stepBuilderFactory
                .get("ProcessingDocumentStep")
                .<Line, Order>chunk(100)
                .reader(myLineReader)
                .processor(myLineProcessor)
                .writer(writer)
                .taskExecutor(taskExecutor())
                .throttleLimit(maxThreads)
                .build();
    }
}
