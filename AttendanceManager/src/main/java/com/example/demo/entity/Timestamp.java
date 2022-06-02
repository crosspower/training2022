package com.example.demo.entity;

public class Timestamp {
	private int id;
	private String user_id;
	private boolean attendance_status;
	private String name;
	private String attendance_time;
	private String leave_time;
	private String attendance_date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public boolean getAttendance_status() {
		return attendance_status;
	}
	public void setAttendance_status(boolean attendance_status) {
		this.attendance_status = attendance_status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAttendance_time() {
		return attendance_time;
	}
	public void setAttendance_time(String attendance_time) {
		this.attendance_time = attendance_time;
	}
	public String getLeave_time() {
		return leave_time;
	}
	public void setLeave_time(String leave_time) {
		this.leave_time = leave_time;
	}
	public String getAttendance_date() {
		return attendance_date;
	}
	public void setAttendance_date(String attendance_date) {
		this.attendance_date = attendance_date;
	}

}
