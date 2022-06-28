package com.example.demo.service;
import java.util.List;
import java.util.Optional;
import com.example.demo.entity.Information;
public interface InformationService {
	
    void save(Information informaion);
    
    List<Information> getAll();
    Optional<Information> getInformation(int id);
}
