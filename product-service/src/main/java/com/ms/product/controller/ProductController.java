package com.ms.product.controller;

import com.ms.product.dto.ProductDTO;
import com.ms.product.entity.Product;
import com.ms.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO dto) {
        return ResponseEntity.ok(service.convertToDTO(service.saveProduct(dto)));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listAll() {
        List<ProductDTO> dtos = service.listAll()
                .stream()
                .map(service::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        Product product = service.findById(id);
        return ResponseEntity.ok(service.convertToDTO(product));
    }
}
