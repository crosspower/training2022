package com.example.demo.service;

import com.example.demo.app.setting.SettingForm;

public interface SettingService {
	
	String getPass(String code);
	
	 void updatePass(SettingForm settingForm);
	
}