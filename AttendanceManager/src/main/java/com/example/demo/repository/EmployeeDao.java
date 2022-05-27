package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Employee;

public interface EmployeeDao {
	
	List<Employee> getAll();
	
	Optional<Employee> findByCode (String code);
	
	int update(Employee employee, String oldCode);
	
	void insert(Employee employee);

}
