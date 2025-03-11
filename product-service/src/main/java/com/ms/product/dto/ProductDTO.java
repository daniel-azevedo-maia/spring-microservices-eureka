package com.ms.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Positive(message = "Preço deve ser positivo")
    private Double price;

    @Positive(message = "Estoque deve ser positivo")
    private Integer stock;
}
