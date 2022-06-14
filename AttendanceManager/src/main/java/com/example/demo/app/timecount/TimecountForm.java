package com.example.demo.app.timecount;

public class TimecountForm {
	private int year;
	private int month;
	
	public TimecountForm() {
	}

	public TimecountForm(int year, int month) {
		this.year = year;
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
}
