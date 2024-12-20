package com.appt.auth.Authentication.config;


import com.appt.auth.Authentication.entity.Person;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

  @Override
  public Person process(Person person) throws Exception {
    String date = person.getDate();
    String projectName = person.getProjectName();
    String jobName = person.getJobName();
    String employeeId = person.getEmployeeId();
    String emailId = person.getEmailId();
    String firstName = person.getFirstName();
    String lastName = person.getLastName();
    String workItem = person.getWorkItem();
    Double hours = person.getHours();
    String billingStatus = person.getBillingStatus();
    String approvalStatus = person.getApprovalStatus();
    String description = person.getDescription();

    Person person1 = new Person(person.getId(), date, projectName, jobName, employeeId, emailId, firstName,
        lastName, workItem, hours, billingStatus, approvalStatus, description);
    return person1;
  }
}
