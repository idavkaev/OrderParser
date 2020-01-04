package com.example.orderparser;

import com.example.orderparser.configuration.OrderProcessorConfiguration;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {OrderProcessorConfiguration.class, TestOrdersOrderParserApplication.TestConfig.class})
//@SuppressWarnings("unchecked")
public class TestOrdersOrderParserApplication {

    private final String PATH = "src/test/resources/";
    private final String EXPECTED_OUTPUT_JSON = PATH + "expected_json.txt";
    private final String EXPECTED_OUTPUT_CSV = PATH + "expected_csv.txt";
    private final String ACTUAL_RESULT = PATH + "result.txt";
    private final String CSV_INPUT = PATH + "sample.csv";
    private final String JSON_INPUT = PATH + "sample.json";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    private JobParameters defaultJobParameters(String filename) {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("file", filename);
        return paramsBuilder.toJobParameters();
    }

    public void clearOutputFile() throws FileNotFoundException {
        //Emptying file for output
        PrintWriter pw = new PrintWriter(ACTUAL_RESULT);
        pw.close();
    }

    @Before
    public void setOut() throws FileNotFoundException {
        // Direct STDOUT to file co compare with expected result
        PrintStream out = new PrintStream(new File(ACTUAL_RESULT));
        System.setOut(out);
        System.out.flush();
    }

    @After
    public void resetOut() throws FileNotFoundException {
        // Resettings system out back to console
        clearOutputFile();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.out.flush();
    }

    private boolean compareFiles(String expected, String actual) throws IOException {
//  UNCOMMENT BELOW FOR TROUBLESHOOTING
//        boolean filesEqual = FileUtils.readLines(new File(expected)).containsAll(FileUtils.readLines(new File(actual)));
//        if (!filesEqual) {
//            List exp = new ArrayList(FileUtils.readLines(new File(expected)));
//            List res = new ArrayList(FileUtils.readLines(new File(actual)));
//
//            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
//            resetOut();
//            System.out.println("Difference:");
//            System.out.println(exp.removeAll(res));
//        }
        return FileUtils.readLines(new File(expected)).containsAll(FileUtils.readLines(new File(actual)));
    }

    @Test
    public void testCsvParsing() throws Exception {
        JobParameters jobParameters = defaultJobParameters(CSV_INPUT);
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("ProcessingDocumentStep", jobParameters);
        Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        Assert.assertTrue(compareFiles(EXPECTED_OUTPUT_CSV, ACTUAL_RESULT));
    }

    @Test
    public void testJsonParsing() throws Exception {
        JobParameters jobParameters = defaultJobParameters(JSON_INPUT);
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("ProcessingDocumentStep", jobParameters);
        Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        Assert.assertTrue(compareFiles(EXPECTED_OUTPUT_JSON, ACTUAL_RESULT));
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

