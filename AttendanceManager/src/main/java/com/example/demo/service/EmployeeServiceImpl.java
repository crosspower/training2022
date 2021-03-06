package com.example.demo.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeDao;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
    MessageSource messagesource;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private final EmployeeDao dao; 
	
	@Autowired
	public EmployeeServiceImpl(EmployeeDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Employee> getAll() {
		return dao.getAll();
	}

	@Override
	public Optional<Employee> getEmployee(String code) {
		try{
			//try-catchで従業員が存在しない例外の拾い上げをする。
			return dao.findByCode(code);
		} catch (EmptyResultDataAccessException e) {
			String message = messagesource.getMessage("E0005", new String[]{"指定された従業員"}, Locale.JAPAN);
			throw new EmployeeNotFoundException(message);
		}
	}

	@Override
	public void update(Employee employee, String oldCode) {
		//従業員情報更新。codeが見つからなければ例外処理
		if(dao.update(employee, oldCode) == 0) {
			String message = messagesource.getMessage("E0005", new String[]{"情報を更新する従業員"}, Locale.JAPAN);
			throw new EmployeeNotFoundException(message);
		}
	}

	@Override
	public void save(Employee employee) {
		// パスワードをハッシュ化
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		dao.insert(employee);
	}

}
