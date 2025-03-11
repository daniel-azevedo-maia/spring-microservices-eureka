package com.ms.order.service;

import com.ms.order.client.ProductClient;
import com.ms.order.dto.OrderDTO;
import com.ms.order.dto.ProductDTO;
import com.ms.order.entity.Order;
import com.ms.order.repository.OrderRepository;
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
        log.info("Consultando produto ID {} via FeignClient", dto.getProductId());
        ProductDTO product = productClient.getProductById(dto.getProductId());

        if (product == null || product.getId() == null) {
            log.error("Produto não encontrado com ID {}", dto.getProductId());
            throw new RuntimeException("Produto não encontrado");
        }

        Double total = product.getPrice() * dto.getQuantity();
        Order order = Order.builder()
                .userId(dto.getUserId())
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .total(total)
                .createdAt(LocalDateTime.now())
                .build();

        log.info("Salvando pedido no banco de dados: {}", order);
        return repository.save(order);
    }

    public List<Order> listAll() {
        return repository.findAll();
    }

    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Pedido com ID {} não encontrado", id);
                    return new RuntimeException("Pedido não encontrado");
                });
    }
}
