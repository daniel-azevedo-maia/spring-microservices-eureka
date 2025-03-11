package com.ms.order.controller;

import com.ms.order.client.ProductClient;
import com.ms.order.dto.OrderDTO;
import com.ms.order.dto.ProductDTO;
import com.ms.order.entity.Order;
import com.ms.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private ProductClient productClient;

    // Apenas para testes
    @GetMapping("/test/product/{id}")
    public ProductDTO testProductClient(@PathVariable Long id) {
        return productClient.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody @Valid OrderDTO dto) {
        return ResponseEntity.ok(service.createOrder(dto));
    }

    @GetMapping
    public ResponseEntity<List<Order>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
