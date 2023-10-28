package com.example.globalgtcbackend.mappers;

import com.example.globalgtcbackend.models.dto.ProductQuotationDTO;
import com.example.globalgtcbackend.models.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;



@Component("ProductMapper")
public class ProductMapper implements Mapper<Product, ProductQuotationDTO>{
    @Override
    public ProductQuotationDTO transform(Product product) {
        var productDto = new ProductQuotationDTO();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    @Override
    public Product transformBack(ProductQuotationDTO productDTO) {
        var product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }
}
