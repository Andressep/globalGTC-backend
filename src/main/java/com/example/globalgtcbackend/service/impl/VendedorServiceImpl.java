package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.models.entity.Vendedor;
import com.example.globalgtcbackend.repository.IVendedorDao;
import com.example.globalgtcbackend.service.IVendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VendedorServiceImpl implements IVendedorService {
    @Autowired
    private IVendedorDao vendedorDao;
    @Override
    @Transactional(readOnly = true)
    public List<Vendedor> allVendedor() {
        return vendedorDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Vendedor findVendedorById(Integer id) {
        return vendedorDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Vendedor saveVendedor(Vendedor vendedor) {
        return vendedorDao.save(vendedor);
    }

    @Override
    @Transactional()
    public void deleteVendedor(Integer id) {
        vendedorDao.deleteById(id);
    }
}
