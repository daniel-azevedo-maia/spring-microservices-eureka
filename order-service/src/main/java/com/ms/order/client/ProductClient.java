package com.ms.order.client;

import com.ms.order.application.dto.ProductDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "PRODUCT-SERVICE",
        path = "/api/products",
        fallback = ProductClientFallback.class
)
public interface ProductClient {
    @GetMapping("/{id}")
    ProductDTO getProductById(@PathVariable("id") Long id);
}
