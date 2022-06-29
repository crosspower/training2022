package com.example.demo.repository;
import java.util.List;
import com.example.demo.entity.Information;
public interface InformationDao {

	List<Information> getAll(String strDate);
	List<Information>Date(int id);
	
    void update(Information information);
}