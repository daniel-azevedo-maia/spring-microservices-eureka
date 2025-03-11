package com.ms.order.controller;

import com.ms.order.dto.OrderDTO;
import com.ms.order.entity.Order;
import com.ms.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderDTO dto) {
        log.info("Recebendo requisição de criação de pedido: {}", dto);
        Order order = orderService.createOrder(dto);
        log.info("Pedido criado com sucesso: {}", order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        log.info("Buscando pedido com ID {}", id);
        return ResponseEntity.ok(orderService.findById(id));
    }
}
