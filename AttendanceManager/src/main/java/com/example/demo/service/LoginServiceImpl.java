package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.demo.app.login.LoginForm;
import com.example.demo.entity.Login;
import com.example.demo.repository.LoginDaoImpl;

@Service
public class LoginServiceImpl implements AuthenticationProvider {
	
	private final LoginDaoImpl loginDao;
	
	@Autowired
	public LoginServiceImpl(
			LoginDaoImpl loginDao) {
		this.loginDao = loginDao;
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		// ID、パスワードを取得
		String code = (String) auth.getPrincipal();
		String password = (String) auth.getCredentials();
		Optional<Login> loginOpt = null;
		
		// 空白の場合
		if("".equals(code) || "".equals(password)) {
			throw new AuthenticationCredentialsNotFoundException("IDまたはパスワードが空白です。");
		}
		
		//　データベースで照合
		try {
		loginOpt = loginDao.check(code, password);
			
		} catch (EmptyResultDataAccessException e) {
			throw new AuthenticationCredentialsNotFoundException("IDまたはパスワードが違います。");
		}
		
		if(!loginOpt.isPresent()) {
			throw new AuthenticationCredentialsNotFoundException("IDまたはパスワードが違います。");
		}

		// 権限をログイン情報に追加 
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		Optional<LoginForm> loginFormOpt = loginOpt.map(l -> makeLoginForm(l));
		LoginForm loginForm = loginFormOpt.get();
		 
		 if (loginForm.getRole() == 1) {
			 authorityList.add(new SimpleGrantedAuthority("admin"));
		 } else {
			 authorityList.add(new SimpleGrantedAuthority("user"));
		 }
		
		 // Tokenに追加
		 UsernamePasswordAuthenticationToken token
			= new UsernamePasswordAuthenticationToken(loginForm.getCode(), loginForm.getPassword(), authorityList);	
		
		// 名前の設定
		token.setDetails(loginForm.getName());
		
		return token;
	}

	@Override
	public boolean supports(Class<?> token) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(token);
	}
	
	// ログイン情報をフォームに追加
	public LoginForm makeLoginForm(Login login) {
		LoginForm loginForm = new LoginForm(login);
		
		loginForm.setCode(login.getCode());
		loginForm.setName(login.getName());
		loginForm.setPassword(login.getPassword());
		loginForm.setRole(login.getRole());
		
		return loginForm;
	}
}