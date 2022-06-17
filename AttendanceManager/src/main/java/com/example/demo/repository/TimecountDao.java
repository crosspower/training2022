package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Timecount;

public interface TimecountDao {
	
	List<Timecount> count(String code, int year, String month);

}
