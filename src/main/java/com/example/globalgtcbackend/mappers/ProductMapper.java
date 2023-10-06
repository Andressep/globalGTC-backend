package com.example.globalgtcbackend.mappers;

import com.example.globalgtcbackend.models.dto.ProductDTO;
import com.example.globalgtcbackend.models.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;
    public ProductMapper() {
        modelMapper = new ModelMapper();
    }

    public ProductDTO toDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
    public Product toEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
    public List<ProductDTO> toDTOList(List<Product> productList) {
        return productList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
