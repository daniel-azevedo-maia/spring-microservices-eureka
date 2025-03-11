package com.ms.order.controller;

import com.ms.order.client.ProductClient;
import com.ms.order.dto.OrderDTO;
import com.ms.order.dto.ProductDTO;
import com.ms.order.entity.Order;
import com.ms.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;
    private final ProductClient productClient;

    @GetMapping("/test/product/{id}")
    public ProductDTO testProductClient(@PathVariable("id") Long id) {
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
