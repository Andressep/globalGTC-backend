package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.exception.ResourceNoFoundException;
import com.example.globalgtcbackend.mappers.Mapper;
import com.example.globalgtcbackend.models.dto.ProductQuotationDTO;
import com.example.globalgtcbackend.models.entity.Category;
import com.example.globalgtcbackend.models.entity.Product;
import com.example.globalgtcbackend.repository.ICategoryDao;
import com.example.globalgtcbackend.repository.IProductDao;
import com.example.globalgtcbackend.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Autowired
    private ICategoryDao categoryDao;

    @Autowired
    @Qualifier("ProductMapper")
    private Mapper<Product, ProductQuotationDTO> productMapper;
    @Override
    @Transactional(readOnly = true)
    public List<ProductQuotationDTO> getAllProducts() {
        var products = productDao.findAll();
        List<ProductQuotationDTO> productDTOS = new ArrayList<>();
        Optional.ofNullable(products).ifPresent(
                product -> product.forEach(
                        eachProduct -> productDTOS.add(productMapper.transform(eachProduct))
                )
        );
        return productDTOS;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductQuotationDTO findProductById(Integer id) {
        var productDTO = productDao.findById(id);
        if (productDTO.isPresent()) return productMapper.transform(productDTO.get());
        throw new ResourceNoFoundException("Product ID is invalid");
    }

    @Override
    @Transactional()
    public ProductQuotationDTO saveProduct(ProductQuotationDTO product) {
        Product products = productMapper.transformBack(product);
        return productMapper.transform(productDao.save(products));
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
