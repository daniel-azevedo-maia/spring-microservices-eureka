package com.ms.product.service;

import com.ms.product.dto.ProductDTO;
import com.ms.product.entity.Product;
import com.ms.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;

    public Product saveProduct(ProductDTO dto) {
        log.info("Salvando produto: {}", dto);
        Product product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();
        return repository.save(product);
    }

    public List<Product> listAll() {
        log.info("Listando todos os produtos...");
        return repository.findAll();
    }

    public Product findById(Long id) {
        log.info("Buscando produto com ID {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Produto com ID {} não encontrado", id);
                    return new RuntimeException("Produto não encontrado");
                });
    }

    public ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
