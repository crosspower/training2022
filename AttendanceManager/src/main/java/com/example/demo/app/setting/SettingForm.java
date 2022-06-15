package com.example.demo.app.setting;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SettingForm {
	
	@NotNull
	private String code;
	
	@Size(max = 20, message = "{Size.password}")
	@NotBlank(message = "{NotBlank.password}")
	private String currentPass;
	
	@Size(max = 20, message = "{Size.password}")
	@NotBlank(message = "{NotBlank.password}")
	private String newPass;
	
	@Size(max = 20, message = "{Size.password}")
	@NotBlank(message = "{NotBlank.password}")
	private String confirmPass;
	
	public SettingForm() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCurrentPass() {
		return currentPass;
	}

	public void setCurrentPass(String currentPass) {
		this.currentPass = currentPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}


}
