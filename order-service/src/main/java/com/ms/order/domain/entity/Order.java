package com.ms.order.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID é obrigatório")
    @Positive(message = "User ID deve ser positivo")
    private Long userId;

    @NotNull(message = "Product ID é obrigatório")
    @Positive(message = "Product ID deve ser positivo")
    private Long productId;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    private Integer quantity;

    @NotNull(message = "Total é obrigatório")
    @PositiveOrZero(message = "Total não pode ser negativo")
    private Double total;

    @NotNull(message = "Data de criação é obrigatória")
    private LocalDateTime createdAt;
}
