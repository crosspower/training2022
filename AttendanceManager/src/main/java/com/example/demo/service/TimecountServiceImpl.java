package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Timecount;
import com.example.demo.repository.TimecountDao;

@Service
public class TimecountServiceImpl implements TimecountService {
	
	private final TimecountDao dao;
	
	@Autowired
	public TimecountServiceImpl(TimecountDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Timecount> getRecord(String code, int year, String month) {
		
		return dao.count(code, year, month);
	}

}
