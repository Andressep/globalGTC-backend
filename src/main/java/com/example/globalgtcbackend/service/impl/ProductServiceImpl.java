package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.mappers.ProductMapper;
import com.example.globalgtcbackend.models.dto.ProductDTO;
import com.example.globalgtcbackend.models.entity.Category;
import com.example.globalgtcbackend.models.entity.Product;
import com.example.globalgtcbackend.repository.ICategoryDao;
import com.example.globalgtcbackend.repository.IProductDao;
import com.example.globalgtcbackend.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Autowired
    private ICategoryDao categoryDao;

    @Autowired
    private ProductMapper productMapper;
    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productMapper.toDTOList(productDao.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Product findProductById(Integer id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Product saveProduct(Product product) {
        return productDao.save(product);
    }

    @Override
    @Transactional()
    public void deleteProduct(Integer id) {
        productDao.deleteById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    @Override
    public Category findCategoryById(Integer id) {
        return categoryDao.findById(id).orElse(null);
    }
}
