package com.example.demo.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Information;
import com.example.demo.repository.InformationDao;
@Service
public class InformationServiceImpl implements InformationService {
    private final InformationDao dao;
    
    @Autowired InformationServiceImpl(InformationDao dao){
        this.dao = dao;
    }
    @Override
    public void save(Information information) {
        dao.updateInformation(information);
    }
    @Override
    public List<Information> getAll() {
        return dao.getAll();
    }
    @Override
    public Optional<Information> getInformation(int id) {
        return null;
    }
}
