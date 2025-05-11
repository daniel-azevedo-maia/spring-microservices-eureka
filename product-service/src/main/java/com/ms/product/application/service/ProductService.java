package com.ms.product.application.service;

import com.ms.product.application.dto.ProductDTO;
import com.ms.product.domain.entity.Product;
import com.ms.product.infrastructure.exception.ProductNotFoundException;
import com.ms.product.mapper.ProductMapper;
import com.ms.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductDTO createProduct(ProductDTO dto) {
        log.info("Salvando produto: {}", dto);
        Product saved = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    public List<ProductDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado com ID: " + id));
        return mapper.toDTO(product);
    }
}
