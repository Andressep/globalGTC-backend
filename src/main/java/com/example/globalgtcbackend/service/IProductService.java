package com.example.globalgtcbackend.service;

import com.example.globalgtcbackend.models.dto.ProductDTO;

import com.example.globalgtcbackend.models.entity.Category;
import com.example.globalgtcbackend.models.entity.Product;

import java.util.List;

public interface IProductService {

    List<ProductDTO> getAllProducts();
    Product findProductById(Integer id);
    Product saveProduct(Product product);
    void deleteProduct(Integer id);

    List<Category> getAllCategories();
    Category findCategoryById(Integer id);
}
