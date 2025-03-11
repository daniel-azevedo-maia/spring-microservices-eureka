package com.ms.order.client;

import com.ms.order.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public ProductDTO getProductById(Long id) {
        return null; // ou lançar uma exceção customizada
    }
}
