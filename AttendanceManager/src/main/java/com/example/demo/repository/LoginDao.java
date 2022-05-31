package com.example.demo.repository;

import java.util.Optional;

import com.example.demo.entity.Login;

public interface LoginDao {
	
	Optional<Login> check(String code, String password);

}
