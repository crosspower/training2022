package com.example.demo.app.information;

import javax.validation.constraints.NotBlank;

public class InformationForm {

	private int id;

	//@NotBlank(message = "退勤時間を入力してください")
	@NotBlank(message = "{NotBlank.attendance}")
	private String attendance_time;

	//@NotBlank(message = "退勤時間を入力してください")
	@NotBlank(message = "{NotBlank.leave}")
	private String leave_time;

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