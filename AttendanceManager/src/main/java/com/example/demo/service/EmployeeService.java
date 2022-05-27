package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Employee;

public interface EmployeeService {
	
	List<Employee> getAll();
	
	Optional<Employee> getEmployee(String code);
	
	void update(Employee employee, String oldCode);
	
	void save(Employee employee);

}
