package com.ms.order.service;

import com.ms.order.client.ProductClient;
import com.ms.order.dto.OrderDTO;
import com.ms.order.dto.ProductDTO;
import com.ms.order.entity.Order;
import com.ms.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final ProductClient productClient;

    public void testComunication() {
        ProductDTO product = productClient.getProductById(1L);
        System.out.println("Produto encontrado via Feign: " + product.getName());
    }

    public Order createOrder(OrderDTO dto) {
        // Consulta o produto pelo ID via Feign Client
        ProductDTO product = productClient.getProductById(dto.getProductId());

        // Calcula o total com base no preço real do produto
        Double total = product.getPrice() * dto.getQuantity();

        Order order = Order.builder()
                .userId(dto.getUserId())
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .total(total)
                .createdAt(LocalDateTime.now())
                .build();

        return repository.save(order);
    }

    public List<Order> listAll() {
        return repository.findAll();
    }

    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }
}
