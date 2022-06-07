package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Login;

@Repository
public class LoginDaoImpl implements LoginDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	public LoginDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/*
	 * データベースで照合を行う
	 */
	@Override
	public Optional<Login> check(String code){
		
		String sql = "SELECT code, name, role, password FROM employees "
		+ "WHERE code = ?";
			
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, code);
		
		// 情報を詰め替え
		Login login = new Login();
		login.setName((String)result.get("name"));
		login.setCode((String)result.get("code"));
		login.setRole((Integer)result.get("role"));
		login.setPassword((String)result.get("password"));
		
		Optional<Login> loginOpt = Optional.ofNullable(login);
		
		return loginOpt;
	}
}
