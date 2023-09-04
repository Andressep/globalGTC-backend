package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.mappers.ProductoDTOMapper;
import com.example.globalgtcbackend.models.dto.ProductoDTO;
import com.example.globalgtcbackend.models.entity.Categoria;
import com.example.globalgtcbackend.models.entity.Producto;
import com.example.globalgtcbackend.repository.ICategoriaDao;
import com.example.globalgtcbackend.repository.ICotizacionDao;
import com.example.globalgtcbackend.repository.IProductoDao;
import com.example.globalgtcbackend.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IProductoServiceImpl implements IProductoService {

    @Autowired
    private IProductoDao productoDao;
    @Autowired
    private ICategoriaDao categoriaDao;
    @Autowired
    private ProductoDTOMapper productoDTOMapper;
    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> allProductos() {
        return productoDao.findAll()
                .stream()
                .map(productoDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findProductoById(Integer id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Producto saveProducto(Producto producto) {
        return productoDao.save(producto);
    }

    @Override
    @Transactional()
    public void deleteProducto(Integer id) {
        productoDao.deleteById(id);
    }

    @Override
    public List<Categoria> allcategoriass() {
        return categoriaDao.findAll();
    }

    @Override
    public Categoria findCategoriaById(Integer id) {
        return categoriaDao.findById(id).orElse(null);
    }
}
