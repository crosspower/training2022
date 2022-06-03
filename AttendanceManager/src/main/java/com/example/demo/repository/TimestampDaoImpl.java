package com.example.demo.repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Timestamp;
@Repository
public class TimestampDaoImpl  implements TimestampDao{
private final JdbcTemplate jdbcTemplate;
	
	public TimestampDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void InsertTimestamp(Timestamp timestamp) {
		if(timestamp.getAttendance_status()) {
			jdbcTemplate.update("UPDATE timestamps SET leave_time = ? WHERE user_id = ? AND attendance_date = (SELECT MAX(attendance_date) FROM timestamps)",
					 getTime(),timestamp.getUser_id());
		}else {
			jdbcTemplate.update("INSERT INTO timestamps(user_id, attendance_time, attendance_date, name) VALUES(?, ?, ?, ?)",
					timestamp.getUser_id(), getTime(), getDate(), timestamp.getName());	
		}
	}
	public String getTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSS"); 
	        String formatNowDate = dtf.format(LocalDateTime.now()); 
	        return formatNowDate;
	}
	
	public String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
	        String formatNowDate = dtf.format(LocalDateTime.now()); 
	        return formatNowDate;
	}
	
	@Override
	public String getAttendance_status(String user_id) {
		String sql = "SELECT leave_time FROM timestamps WHERE user_id = ?  ORDER BY attendance_date DESC LIMIT 1";
		try{
			Map<String, Object> result = jdbcTemplate.queryForMap(sql, user_id);
			if(result.get("leave_time") != null) {
				return "complete";
			}else {
				return "leave";
			}
		}
		catch(Exception e) {
			return "attend";
		}
	}
}
