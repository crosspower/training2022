package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.app.setting.SettingForm;
import com.example.demo.repository.SettingDao;

@Service
public class SettingServiceImpl implements SettingService {

	@Autowired
	PasswordEncoder passwordEncoder;

	private final SettingDao dao;

	@Autowired
	SettingServiceImpl(SettingDao dao) {
		this.dao = dao;
	}

	@Override
	public String getPass(String code) {
		return dao.getPass(code);
	}

	@Override
	public void updatePass(SettingForm settingForm) {
		settingForm.setNewPass(passwordEncoder.encode(settingForm.getNewPass())); // 入力されたパスワードの暗号化
		dao.updatePass(settingForm);
	}

}
