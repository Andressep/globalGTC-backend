package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.models.entity.Producto;
import com.example.globalgtcbackend.repository.IProductoDao;
import com.example.globalgtcbackend.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IProductoServiceImpl implements IProductoService {

    @Autowired
    private IProductoDao productoDao;
    @Override
    public List<Producto> allProductos() {
        return productoDao.findAll();
    }

    @Override
    public Producto findProductoById(Integer id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    public Producto saveProducto(Producto producto) {
        return productoDao.save(producto);
    }

    @Override
    public void deleteProducto(Integer id) {
        productoDao.deleteById(id);
    }
}
