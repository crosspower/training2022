package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	MessageSource messagesource;
	
	@Autowired
	@Lazy
	PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		// ID、パスワードを取得
		String code = (String) auth.getPrincipal();
		String password = (String) auth.getCredentials();
		Optional<Login> loginOpt = null;
		
		// エラーメッセージ
		String failure_message = messagesource.getMessage("E0003", null, Locale.JAPAN);
		String null_message = messagesource.getMessage("E0004", null, Locale.JAPAN);
		// IDとパスワードの文字数を両方超えた場合
		//　「文字数に失敗しました」と一度エラーを発生させる。
		String over_message = messagesource.getMessage("E0002", new String[]{"文字数"}, Locale.JAPAN);
		String over_id_message = messagesource.getMessage("E0008", new String[]{"ID", "10"}, Locale.JAPAN);
		String over_pass_message = messagesource.getMessage("E0008", new String[]{"パスワード", "20"}, Locale.JAPAN);
		
		// 空白の場合
		if("".equals(code) || "".equals(password)) {
			throw new AuthenticationCredentialsNotFoundException(null_message);
		}
		//　文字数制限の場合
		if (code.length() > 10) {
			if (password.length() > 20) {
				throw new AuthenticationCredentialsNotFoundException(over_message);
			} else {
				throw new AuthenticationCredentialsNotFoundException(over_id_message);
			}
		}
		if (password.length() > 20) {
			throw new AuthenticationCredentialsNotFoundException(over_pass_message);	
		}
		
		//　データベースで照合
		try {
		loginOpt = loginDao.check(code);
			
		} catch (EmptyResultDataAccessException e) {
			throw new AuthenticationCredentialsNotFoundException(failure_message);
		}
		
		if(!loginOpt.isPresent()) {
			throw new AuthenticationCredentialsNotFoundException(failure_message);
		}

		// 権限をログイン情報に追加 
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		Optional<LoginForm> loginFormOpt = loginOpt.map(l -> makeLoginForm(l));
		LoginForm loginForm = loginFormOpt.get();
	
		if(!passwordEncoder.matches(password, loginForm.getPassword())) {
			throw new AuthenticationCredentialsNotFoundException(failure_message);
		}
		 
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