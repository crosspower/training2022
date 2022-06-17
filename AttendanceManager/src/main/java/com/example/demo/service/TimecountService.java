package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Timecount;

public interface TimecountService {
	
	List<Timecount> getRecord(String code, int year, String month);
	
}
