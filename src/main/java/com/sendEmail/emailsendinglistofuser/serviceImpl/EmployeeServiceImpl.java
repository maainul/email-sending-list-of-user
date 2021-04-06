package com.sendEmail.emailsendinglistofuser.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sendEmail.emailsendinglistofuser.model.Employee;
import com.sendEmail.emailsendinglistofuser.repository.EmployeeRepository;
import com.sendEmail.emailsendinglistofuser.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployeeList() {
		return employeeRepository.findAll();

	}

	@Override
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);

	}

	@Override
	public Employee findEmployeeById(Integer id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException("No Expense Found for id = " + id);
		}
		return employee;
	}

}
