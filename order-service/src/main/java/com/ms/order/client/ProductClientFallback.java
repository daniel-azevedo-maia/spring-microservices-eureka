package com.ms.order.client;

import com.ms.order.application.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public ProductDTO getProductById(Long id) {
        return ProductDTO.builder()
                .id(id)
                .name("Produto Indisponível")
                .price(0.0)
                .stock(0)
                .build();
    }
}
