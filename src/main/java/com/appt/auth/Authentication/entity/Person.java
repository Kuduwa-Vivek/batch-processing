package com.appt.auth.Authentication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "date")
  private String date;

  @Column(name = "project_name")
  private String projectName;

  @Column(name = "job_name")
  private String jobName;

  @Column(name = "employee_id")
  private String employeeId;

  @Column(name = "email_id")
  private String emailId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "work_item")
  private String workItem;

  @Column(name = "hours")
  private Double hours;

  @Column(name = "billing_status")
  private String billingStatus;

  @Column(name = "approval_status")
  private String approvalStatus;

  @Column(name = "description")
  private String description;

}
