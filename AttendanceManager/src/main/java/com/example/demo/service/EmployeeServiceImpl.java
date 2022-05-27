package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeDao;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
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
			throw new EmployeeNotFoundException("指定された従業員が存在しません");
		}
	}

	@Override
	public void update(Employee employee, String oldCode) {
		//従業員情報更新。codeが見つからなければ例外処理
		if(dao.update(employee, oldCode) == 0) {
			throw new EmployeeNotFoundException("情報を更新する従業員が存在しません");
		}
	}

	@Override
	public void save(Employee employee) {
		dao.insert(employee);
	}

}
