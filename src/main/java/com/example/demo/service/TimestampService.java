package com.example.demo.service;

import com.example.demo.entity.Timestamp;

public interface TimestampService {
		
		void Insert(Timestamp timestamp);
		
		boolean getAttendance_status(String user_id);
}
