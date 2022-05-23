package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Timestamp;
import com.example.demo.repository.TimestampDao;
@Service
public class TimestampServiceImpl implements TimestampService{
private final TimestampDao dao;
	@Autowired
	public TimestampServiceImpl(TimestampDao dao) {
		this.dao = dao;
	}
	
	@Override
	public void Insert(Timestamp timestamp) {
		dao.InsertTimestamp(timestamp);
	}
	
	@Override
	public boolean getAttendance_status(String user_id) {
		return dao.getAttendance_status(user_id);
	}
}
