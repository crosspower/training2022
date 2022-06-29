package com.example.demo.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.app.setting.SettingForm;

@Repository
public class SettingDaoImpl implements SettingDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public SettingDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// 社員番号から、現在のパスワードを取得
	@Override
	public String getPass(String code) {
		String sql = "SELECT * FROM employees WHERE code =  ?";
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, code);
		SettingForm settingForm = new SettingForm();
		settingForm.setCurrentPass((String) result.get("password"));

		return settingForm.getCurrentPass();
	}

	// 社員番号から、新しいパスワードを更新
	@Override
	public int updatePass(SettingForm settingForm) {
		return jdbcTemplate.update("UPDATE employees SET password = ? WHERE code =  ?", 
				settingForm.getNewPass(),
				settingForm.getCode());
	}

}