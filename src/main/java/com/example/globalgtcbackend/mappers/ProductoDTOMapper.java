package com.example.globalgtcbackend.mappers;

import com.example.globalgtcbackend.models.dto.ProductoDTO;
import com.example.globalgtcbackend.models.entity.Producto;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class ProductoDTOMapper implements Function<Producto, ProductoDTO> {
    @Override
    public ProductoDTO apply(Producto producto) {
        return new ProductoDTO(
                producto.getProducto_id(),
                producto.getName(),
                producto.getPrice(),
                producto.getWeight()
        );
    }
}
