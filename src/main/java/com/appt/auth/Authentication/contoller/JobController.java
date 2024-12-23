package com.appt.auth.Authentication.contoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {

  @Autowired private JobLauncher jobLauncher;

  @Autowired private Job job;

  private static final Logger logger = LoggerFactory.getLogger(JobController.class);

  @PostMapping("/import")
  public void importCsvToDatabase() {
    JobParameters jobParameters =
        new JobParametersBuilder().addLong("startAt", System.currentTimeMillis()).toJobParameters();

    try{
      logger.info("Starting job with parameters: {}", jobParameters);
      jobLauncher.run(job, jobParameters);
      logger.info("Job executed successfully.");
    } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
             JobParametersInvalidException | JobRestartException e) {
      logger.error("An error occurred while executing thr job: {}", e.getMessage(),e);
    }
  }
}
