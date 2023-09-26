package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.models.entity.Salesperson;
import com.example.globalgtcbackend.repository.ISalespersonDao;
import com.example.globalgtcbackend.service.ISalespersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalespersonServiceImpl implements ISalespersonService {
    @Autowired
    private ISalespersonDao salespersonDao;

    @Override
    @Transactional(readOnly = true)
    public List<Salesperson> getAllSalespeople() {
        return salespersonDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Salesperson findSalespersonById(Integer id) {
        return salespersonDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Salesperson saveSalesperson(Salesperson salesperson) {
        return salespersonDao.save(salesperson);
    }

    @Override
    @Transactional()
    public void deleteSalesperson(Integer id) {
        salespersonDao.deleteById(id);
    }
}
