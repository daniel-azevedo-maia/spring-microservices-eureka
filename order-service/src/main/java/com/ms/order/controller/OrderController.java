package com.ms.order.controller;

import com.ms.order.client.ProductClient;
import com.ms.order.dto.ProductDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final ProductClient productClient;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestParam Long productId) {

        ProductDTO product = productClient.getProductById(productId);

        if(product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }

        // Aqui você poderia persistir o pedido, mas por enquanto vamos apenas simular:
        return ResponseEntity.ok("Pedido criado com produto: " + product.getName() + ", valor: R$" + product.getPrice());

    }

}
