package com.example.demo.app.login;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.demo.entity.Login;

public class LoginForm extends User{
	
	private static final long serialVersionUID = 1L;
	
	public String code;
	public String name;
	public Integer role;
	public String password;
	
	public LoginForm(
			Login login) {
		
		super(login.getCode(), login.getPassword(), true, true, true, true, new ArrayList<GrantedAuthority>());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
