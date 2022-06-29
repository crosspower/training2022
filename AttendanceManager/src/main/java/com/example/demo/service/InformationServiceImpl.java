package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Information;
import com.example.demo.repository.InformationDao;

@Service
public class InformationServiceImpl implements InformationService {
	private final InformationDao dao;

	@Autowired
	InformationServiceImpl(InformationDao dao) {
		this.dao = dao;
	}

	@Override
	public void save(Information information) {
		dao.update(information);
	}

	@Override
	public List<Information> getAll(String strDate) {
		return dao.getAll(strDate);
	}
	
	@Override
	public List<Information> getDate(int id) {
		return dao.Date(id);
		/*
		try {
		return dao.Date();
		}catch(NullPointerException n){
			throw new InformationNotFoundException("指定されたidが見つかりません");
		}*/
	}
}