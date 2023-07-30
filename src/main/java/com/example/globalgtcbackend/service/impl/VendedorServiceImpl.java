package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.models.entity.Vendedor;
import com.example.globalgtcbackend.repository.IVendedorDao;
import com.example.globalgtcbackend.service.IVendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorServiceImpl implements IVendedorService {
    @Autowired
    private IVendedorDao vendedorDao;
    @Override
    public List<Vendedor> allVendedor() {
        return vendedorDao.findAll();
    }

    @Override
    public Vendedor findVendedorById(Integer id) {
        return vendedorDao.findById(id).orElse(null);
    }

    @Override
    public Vendedor saveVendedor(Vendedor vendedor) {
        return vendedorDao.save(vendedor);
    }

    @Override
    public void deleteVendedor(Integer id) {
        vendedorDao.deleteById(id);
    }
}
