package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public EmployeeDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Employee> getAll() {
		String sql = "SELECT code, name, role, password FROM employees";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		
		List<Employee> list = new ArrayList<Employee>();
		
		for(Map<String, Object> result :resultList) {
			
			Employee employee = new Employee();
			
			employee.setCode((String)result.get("code"));
			employee.setName((String)result.get("name"));
			employee.setRole((int)result.get("role"));
			employee.setPassword((String)result.get("password"));
			
			list.add(employee);
		}
		
		
		return list;
	}

	@Override
	public Optional<Employee> findByCode(String code) {
		String sql ="SELECT code, name, role FROM employees WHERE code = ?";
		
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, code);
		
		Employee employee = new Employee();
		employee.setCode((String)result.get("code"));
		employee.setName((String)result.get("name"));
		employee.setRole((int)result.get("role"));
		
		Optional<Employee> employeeOpt = Optional.ofNullable(employee);
		
		return employeeOpt;
	}

	@Override
	public int update(Employee employee, String oldCode) {
		return jdbcTemplate.update("UPDATE employees SET code = ?, name = ?, role = ? WHERE code = ?",
				employee.getCode(), employee.getName(), employee.getRole(),oldCode/*４つ目は変更前の社員番号*/);
	}

	@Override
	public void insert(Employee employee) {
		jdbcTemplate.update("INSERT INTO employees (code, name, password, role) VALUES (?, ?, ?, ?)",
				employee.getCode(), employee.getName(), employee.getPassword(), employee.getRole());
		
	}


}
