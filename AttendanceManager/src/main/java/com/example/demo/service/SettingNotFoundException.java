package com.example.demo.service;

public class SettingNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SettingNotFoundException(String message) {
		super(message);
	}

}