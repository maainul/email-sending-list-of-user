package com.sendEmail.emailsendinglistofuser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sendEmail.emailsendinglistofuser.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
