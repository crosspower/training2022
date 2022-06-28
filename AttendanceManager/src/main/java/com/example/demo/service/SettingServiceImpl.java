package com.example.demo.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.app.setting.SettingForm;
import com.example.demo.repository.SettingDao;

@Service
public class SettingServiceImpl implements SettingService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	MessageSource messagesource;

	private final SettingDao dao;

	@Autowired
	SettingServiceImpl(SettingDao dao) {
		this.dao = dao;
	}

	@Override
	public String getPass(String code) {
		try {
			return dao.getPass(code);
		} catch (EmptyResultDataAccessException e) { // 更新出来なかった場合
			throw new SettingNotFoundException(
					messagesource.getMessage("E0005", new String[] { "データベースにデータ" }, Locale.JAPAN));
		}
	}

	@Override
	public void updatePass(SettingForm settingForm) {
		settingForm.setNewPass(passwordEncoder.encode(settingForm.getNewPass())); // 入力されたパスワードの暗号化
		if (dao.updatePass(settingForm) == 0) { // 更新出来なかった場合
			throw new SettingNotFoundException(
					messagesource.getMessage("E0005", new String[] { "データベースにデータ" }, Locale.JAPAN));
		}
		dao.updatePass(settingForm);
	}

}