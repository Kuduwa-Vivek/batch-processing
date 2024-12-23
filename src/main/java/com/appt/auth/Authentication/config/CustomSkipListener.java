package com.appt.auth.Authentication.config;

import com.appt.auth.Authentication.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

public class CustomSkipListener implements SkipListener<Person, Person> {

  Logger logger = LoggerFactory.getLogger(CustomSkipListener.class);

  @Override
  public void onSkipInRead(Throwable throwable) {
    System.err.println("Skipped during reading: " + throwable.getMessage());
    logger.warn("Skipped during reading", throwable);
  }

  @Override
  public void onSkipInWrite(Person item, Throwable throwable) {
    System.err.println("Skipped writing person: " + item + "due to " + throwable.getMessage());
    logger.warn("Skipped writing person: {} due to {}", item, throwable.getMessage(), throwable);
  }

  @Override
  public void onSkipInProcess(Person item, Throwable throwable) {
    System.err.println("Skipped processing item: " + item + " due to " + throwable.getMessage());
    logger.warn("Skipped processing item: {} due to {}", item, throwable.getMessage(), throwable);
  }
}
