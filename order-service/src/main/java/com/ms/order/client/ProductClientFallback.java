package com.ms.order.client;

import com.ms.order.application.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {
    @Override
    public ProductDTO getProductById(Long id) {
        throw new RuntimeException("Fallback: Produto temporariamente indisponível.");
    }
}