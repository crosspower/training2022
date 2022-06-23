package com.example.demo.app.information;

import javax.validation.constraints.Pattern;

public class InformationForm {

	private int id;
	
	@Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message="{Pattern.time}")
	private String attendance_time;

	@Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message="{Pattern.time}")
	private String leave_time;

	//@DateTimeFormat(pattern = "MM/dd")
	private String attendance_date;

	public InformationForm() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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