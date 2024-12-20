package com.appt.auth.Authentication.config;

import com.appt.auth.Authentication.entity.Person;
import com.appt.auth.Authentication.repo.PersonRepository;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;

import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.data.RepositoryItemWriter;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

  @Autowired private JobBuilderFactory jobBuilderFactory;

  @Autowired private StepBuilderFactory stepBuilderFactory;

  @Autowired private PersonRepository personRepository;

  @Bean
  public FlatFileItemReader<Person> reader() {
    FlatFileItemReader<Person> itemReader = new FlatFileItemReader<>();
    itemReader.setResource(new FileSystemResource("src/main/resources/timeSheet.csv"));
    itemReader.setName("csvReader");
    itemReader.setLinesToSkip(1);
    itemReader.setLineMapper(lineMapper());
    return itemReader;
  }

  @Bean
  private LineMapper<Person> lineMapper() {
    DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setDelimiter(",");
    lineTokenizer.setStrict(false);
    lineTokenizer.setNames(
        "id",
        "date",
        "projectName",
        "jobName",
        "employeeId",
        "emailId",
        "firstName",
        "lastName",
        "workItem",
        "hours",
        "billingStatus",
        "approvalStatus",
        "description");

    BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setTargetType(Person.class);

    lineMapper.setLineTokenizer(lineTokenizer);
    lineMapper.setFieldSetMapper(fieldSetMapper);
    return lineMapper;
  }

  @Bean
  public RepositoryItemWriter<Person> writer() {
    RepositoryItemWriter<Person> writer = new RepositoryItemWriter<>();
    writer.setRepository(personRepository);
    writer.setMethodName("save");
    return writer;
  }

  @Bean
  public PersonItemProcessor processor() {
    return new PersonItemProcessor();
  }

  public Step step1() {
    return stepBuilderFactory
        .get("scv-step")
        .<Person, Person>chunk(10)
        .reader(reader())
        .processor(processor())
        .writer(writer())
        .build();
  }

  @Bean
  public Job runJob(){
    return jobBuilderFactory.get("import").flow(step1()).end().build();
  }

  //  @Bean
  //  public FlatFileItemReader<Person> reader() {
  //    return new FlatFileItemReaderBuilder<Person>().name("timeSheetReader")
  //        .resource(new ClassPathResource(
  //            "timeSheet.csv")).delimited()
  //        .names("id", "date", "projectName", "jobName", "employeeId", "emailId", "firstName",
  //            "lastName", "workItem", "hours", "billingStatus", "approvalStatus", "description")

  //        .targetType(Person.class).build();

  //  }

  //  @Bean
  //  public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
  //    return new JdbcBatchItemWriterBuilder<Person>().sql(
  //            "INSERT INTO person(date,project_name, job_name, employee_id,  email_id,
  // first_name,  last_name, work_item,  hours,  billing_status,  approval_status,  description)
  // VALUES (  :date,  :projectName,  :jobName,  :employeeId, :emailId, :firstName,  :lastName,
  // :workItem,  :hours,  :billingStatus,  :approvalStatus,  :description))")
  //        .dataSource(dataSource).beanMapped().build();
  //  }
  //
  //  @Bean
  //  public Job importUserJob(JobRepository jobRepository, Step step1,
  //      JobCompletionNotificationListener listener) {
  //    return new JobBuilder("importUserJob", jobRepository)
  //        .listener(listener)
  //        .start(step1)
  //        .build();
  //  }
  //
  //  @Bean
  //  public Step step1(JobRepository jobRepository, DataSourceTransactionManager
  // transactionManager,
  //      FlatFileItemReader<Person> reader, PersonItemProcessor processor,
  //      JdbcBatchItemWriter<Person> writer) {
  //    return new StepBuilder("step1", jobRepository)
  //        .<Person, Person>chunk(3, transactionManager)
  //        .reader(reader)
  //        .processor(processor)
  //        .writer(writer)
  //        .build();
  //  }

}
