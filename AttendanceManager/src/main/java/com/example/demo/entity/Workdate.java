package com.example.demo.entity;

public class Workdate {
	
	private String attendanceTime;
	private String leaveTime;
	private String attendanceDate;
	
	public Workdate() {
	}

	public String getAttendanceTime() {
		return attendanceTime;
	}

	public void setAttendanceTime(String attendanceTime) {
		this.attendanceTime = attendanceTime;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
}