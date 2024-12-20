package com.appt.auth.Authentication.repo;


import com.appt.auth.Authentication.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long>{

}
