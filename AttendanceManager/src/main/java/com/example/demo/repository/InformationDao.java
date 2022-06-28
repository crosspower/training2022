package com.example.demo.repository;
import java.util.List;
import java.util.Optional;
import com.example.demo.entity.Information;
public interface InformationDao {

	List<Information> getAll();
    Optional<Information> findById(int id);
    void updateInformation(Information information);
    
}