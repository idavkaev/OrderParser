package com.example.orders_parser;

import com.example.orders_parser.configuration.OrderProcessorConfiguration;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;




@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {OrderProcessorConfiguration.class, OrdersOrderParserApplicationTests.TestConfig.class})
public class OrdersOrderParserApplicationTests {

    private final String PATH = "src/test/resources/";
    private final String EXPECTED_OUTPUT_JSON = PATH + "expected_json.txt";
    private final String EXPECTED_OUTPUT_CSV = PATH + "expected_csv.txt";
    private final String TEST_OUTPUT = PATH + "actual_result.txt";
    private final String CSV_INPUT = PATH + "sample.csv";
    private final String JSON_INPUT = PATH + "sample.json";


    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    private JobParameters defaultJobParameters(String filename) {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("file", filename);
        return paramsBuilder.toJobParameters();
    }

    @Before
    public void clearOutputFile() throws FileNotFoundException {
        //Emptying file for output
        PrintWriter pw = new PrintWriter(TEST_OUTPUT);
        pw.close();

    }

    private boolean compareFiles(String file1, String file2) throws IOException {
        return FileUtils.readLines(new File(file1)).containsAll(FileUtils.readLines(new File(file2)));
    }

    @Test
    public void testJsonParsing() throws Exception {
        JobParameters jobParameters = defaultJobParameters(JSON_INPUT);
        PrintStream o = new PrintStream(new File(TEST_OUTPUT));
        System.setOut(o);
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("ProcessingDocumentStep", jobParameters);
        Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        Assert.assertTrue(compareFiles(EXPECTED_OUTPUT_JSON, TEST_OUTPUT));
    }

    @Test
    public void testCsvParsing() throws Exception {
        JobParameters jobParameters = defaultJobParameters(CSV_INPUT);
        PrintStream o = new PrintStream(new File(TEST_OUTPUT));
        System.setOut(o);
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("ProcessingDocumentStep", jobParameters);
        Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        Assert.assertTrue(compareFiles(EXPECTED_OUTPUT_CSV, TEST_OUTPUT));
    }


    @Configuration
    static class TestConfig {
        @Bean
        public JobLauncherTestUtils getJobLauncherTestUtils() {

            return new JobLauncherTestUtils() {
                @Override
                @Autowired
                public void setJob( Job job) {
                    super.setJob(job);
                }
            };
        }

    }
}

