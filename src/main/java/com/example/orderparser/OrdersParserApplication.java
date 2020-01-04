package com.example.orderparser;

import com.example.orderparser.configuration.OrderProcessorConfiguration;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.Job;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootApplication
public class OrdersParserApplication  {

    public static void main(String[] args) throws Exception{
        GenericApplicationContext context = new AnnotationConfigApplicationContext(OrderProcessorConfiguration.class);
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean(Job.class);

        for (String i : args) {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("file", i).toJobParameters();
            jobLauncher.run(job, jobParameters);
        }
    }
}
