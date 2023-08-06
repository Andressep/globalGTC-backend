package com.example.globalgtcbackend.service;

import com.example.globalgtcbackend.models.entity.Producto;

import java.util.List;

public interface IProductoService {
    public List<Producto> allProductos();
    public Producto findProductoById(Integer id);
    public Producto saveProducto(Producto producto);
    public void deleteProducto(Integer id);
}
