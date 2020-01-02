package com.example.orders_parser;

import com.example.orders_parser.configuration.OrderProcessorConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.*;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;




@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableAutoConfiguration//(exclude = {SimpleBatchConfiguration.class})
@ContextConfiguration(classes = {OrderProcessorConfiguration.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class OrdersOrderParserApplicationTests {

    private final String PATH = "src/test/resources/";
    private final String EXPECTED_OUTPUT = PATH + "expected_output.json";
    private final String TEST_OUTPUT = PATH + "test_output.json";
    private final String CSV_INPUT = PATH + "sample.csv";
    private final String JSON_INPUT = PATH + "sample.json";

    @Autowired
    private FlatFileItemReader<Line> csvReader;

    @Autowired
    private ItemProcessor<Line, Order> itemProcessor;

    @Autowired
    private ItemWriter<Order> itemWriter;

//    private JobLauncherTestUtils jobLauncherTestUtils;



//    @Test
//    public void testJob() throws Exception {
//
//        JobExecution jobExecution = jobLauncherTestUtils.launchStep("ProcessingDocumentStep");
//
//
//        Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
//    }
//
//    private JobParameters defaultJobParameters() {
//        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
//        paramsBuilder.addString("file.input", CSV_INPUT);
//        paramsBuilder.addString("file.output", TEST_OUTPUT);
//        return paramsBuilder.toJobParameters();
//    }



    public JobLauncherTestUtils getJobLauncherTestUtils() {

        return new JobLauncherTestUtils() {
            @Override
            @Autowired
            public void setJob(@Qualifier("OrderParserJob") Job job) {
                super.setJob(job);
            }
        };
    }



    @Test
    public void testStep() throws Exception {
        // given
        JobLauncherTestUtils jobLauncherTestUtils = getJobLauncherTestUtils();
        JobParameters jobParameters = jobLauncherTestUtils.getUniqueJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("ProcessingDocumentStep", jobParameters);

        // then
        Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    @Configuration
    static class TestConfig {
        @Bean
        public JobLauncherTestUtils getJobLauncherTestUtils() {

            return new JobLauncherTestUtils() {
                @Override
                @Autowired
                public void setJob(@Qualifier("OrderParserJob") Job job) {
                    super.setJob(job);
                }
            };
        }

    }
}

