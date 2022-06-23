package com.example.demo.repository;

import com.example.demo.app.setting.SettingForm;

public interface SettingDao {
	
	String getPass(String code) ;
	
	int updatePass(SettingForm settingForm); 

}
