package com.example.globalgtcbackend.mappers;

import com.example.globalgtcbackend.models.dto.ProductDTO;
import com.example.globalgtcbackend.models.entity.Product;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class ProductDTOMapper implements Function<Product, ProductDTO> {
    @Override
    public ProductDTO apply(Product product) {
        return new ProductDTO(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getWeight()
        );
    }
}

