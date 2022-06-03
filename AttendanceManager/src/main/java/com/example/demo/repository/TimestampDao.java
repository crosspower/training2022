package com.example.demo.repository;

import com.example.demo.entity.Timestamp;

public interface TimestampDao {
	void InsertTimestamp(Timestamp timestamp);
	
	String getAttendance_status(String user_id);

}
