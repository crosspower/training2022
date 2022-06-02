package com.example.demo.app.timestamp;

public class TimestampForm {
	
	private String user_id;
	private String name;
	private boolean attendance_status;

    public TimestampForm(String user_id,String name,boolean attendance_status) {
		super();
		this.user_id = user_id;
		this.name = name;
		this.attendance_status = attendance_status;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getAttendance_status() {
		return attendance_status;
	}

	public void setAttendanc_status(boolean attendance_status) {
		this.attendance_status = attendance_status;
	}


}
