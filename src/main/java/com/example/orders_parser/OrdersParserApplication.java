package com.example.orders_parser;

import com.example.orders_parser.configuration.OrderProcessorConfiguration;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootApplication
public class OrdersParserApplication  {


    @Autowired
    OrderParserBean orderParserBean;

    @Autowired
    ApplicationArguments applicationArguments;

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

//
//    @Override
//    public void run(String... args) throws Exception {
//        for (String p : applicationArguments.getNonOptionArgs()) {
//            System.out.println("AAAAAAAAAAAAAAAAA " +p);
//        }
//
//        JobParametersBuilder parametersBuilder = new JobParametersBuilder();
//        parametersBuilder.addString("file", args[0]);
//        jobLauncher.run(job, parametersBuilder.toJobParameters());
//        for (String i : args) {
//            System.out.println(i);
//
//            try (BufferedReader reader = new BufferedReader(new FileReader(i))) {
//                int lineCounter = 0;
//                String line = reader.readLine();
//
//                while (line != null) {
//                    lineCounter ++;
//
//                    String extension = FilenameUtils.getExtension(i);
//                    Order order;
//                    if (EnumUtils.isValidEnumIgnoreCase(Extensions.class, extension)) {
//                        try {
//                            order = orderParserBean.parse(line, EnumUtils.getEnumIgnoreCase(Extensions.class, extension));
//                            order.setFileName(i);
//
//                        } catch(JsonMappingException | JsonParseException e) {
//                            order = new Order(e.getLocalizedMessage(), lineCounter, i, false);
//                        }
//                        System.out.println(order);
//                    }
//                    line = reader.readLine();
//                }
//
//                } catch(Exception e){
//                    throw e;
//                }
//        }
//    }
}
