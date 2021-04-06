package com.sendEmail.emailsendinglistofuser.service;

import java.util.List;

import com.sendEmail.emailsendinglistofuser.model.Employee;
import com.sun.el.stream.Optional;

public interface EmployeeService {
	
	List<Employee> getAllEmployeeList();
	
	void saveEmployee(Employee employee);
	
	Employee findEmployeeById(Integer id);
	
}
