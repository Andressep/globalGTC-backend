package com.example.globalgtcbackend.service;

import com.example.globalgtcbackend.models.dto.ProductQuotationDTO;
import com.example.globalgtcbackend.models.entity.Category;

import java.util.List;

public interface IProductService {

    List<ProductQuotationDTO> getAllProducts();
    ProductQuotationDTO findProductById(Integer id);
    ProductQuotationDTO saveProduct(ProductQuotationDTO product);
    void deleteProduct(Integer id);
    List<Category> getAllCategories();
    Category findCategoryById(Integer id);
}