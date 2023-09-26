package com.example.globalgtcbackend.service;


import com.example.globalgtcbackend.models.entity.Salesperson;

import java.util.List;

public interface ISalespersonService {

    List<Salesperson> getAllSalespeople();
    Salesperson findSalespersonById(Integer id);
    Salesperson saveSalesperson(Salesperson salesperson);
    void deleteSalesperson(Integer id);
}
