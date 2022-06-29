package com.example.demo.service;
import java.util.List;
import com.example.demo.entity.Information;
public interface InformationService {
	
    void save(Information informaion);
    
    List<Information> getAll(String strDate);
    List<Information>getDate(int id);
   
}
