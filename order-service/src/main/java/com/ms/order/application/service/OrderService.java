package com.ms.order.application.service;

import com.ms.order.client.ProductClient;
import com.ms.order.application.dto.OrderDTO;
import com.ms.order.application.dto.ProductDTO;
import com.ms.order.domain.entity.Order;
import com.ms.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repository;
    private final ProductClient productClient;

    public Order createOrder(OrderDTO dto) {
        log.info("Buscando produto ID {} via FeignClient", dto.getProductId());

        // O FeignClient faz uma chamada REST para ProductService
        ProductDTO product = productClient.getProductById(dto.getProductId());

        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Produto não encontrado com ID: " + dto.getProductId());
        }

        Order order = Order.builder()
                .userId(dto.getUserId())
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .total(product.getPrice() * dto.getQuantity())
                .createdAt(LocalDateTime.now())
                .build();

        return repository.save(order);
    }

    public List<Order> listAll() {
        return repository.findAll();
    }

    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + id));
    }
}
