package com.example.globalgtcbackend.service;

import com.example.globalgtcbackend.models.dto.ProductoDTO;
import com.example.globalgtcbackend.models.entity.Categoria;
import com.example.globalgtcbackend.models.entity.Producto;

import java.util.List;

public interface IProductoService {
    public List<ProductoDTO> allProductos();
    public Producto findProductoById(Integer id);
    public Producto saveProducto(Producto producto);
    public void deleteProducto(Integer id);

    public List<Categoria> allcategoriass();
    public Categoria findCategoriaById(Integer id);
}
