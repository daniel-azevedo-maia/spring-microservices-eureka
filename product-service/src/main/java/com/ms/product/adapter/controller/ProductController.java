package com.ms.product.adapter.controller;

import com.ms.product.application.dto.ProductDTO;
import com.ms.product.application.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO dto) {
        log.info("Recebendo requisição para criação de produto: {}", dto);
        return ResponseEntity.ok(service.createProduct(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listAll() {
        log.info("Recebendo requisição para listagem de produtos");
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        log.info("Recebendo requisição para busca de produto por ID: {}", id);
        return ResponseEntity.ok(service.findById(id));
    }
}
