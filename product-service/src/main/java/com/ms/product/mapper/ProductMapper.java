package com.ms.product.mapper;

import com.ms.product.application.dto.ProductDTO;
import com.ms.product.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDTO dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();
    }

    public ProductDTO toDTO(Product entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .build();
    }
}
