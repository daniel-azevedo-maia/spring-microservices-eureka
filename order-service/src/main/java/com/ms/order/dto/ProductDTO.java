package com.ms.order.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;         // <-- ESTE CAMPO É O QUE ESTÁ FALTANDO
    private String name;
    private Double price;
    private Integer stock;
}