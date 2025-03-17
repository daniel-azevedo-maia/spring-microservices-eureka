package com.ms.user.adapter.controller;

import com.ms.user.application.dto.UserDTO;
import com.ms.user.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO dto) {
        log.info("Recebida requisição para criar usuário: {}", dto);
        return ResponseEntity.ok(service.createUser(dto));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listAll() {
        log.info("Recebida requisição para listar usuários.");
        return ResponseEntity.ok(service.listAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        log.info("Recebida requisição para buscar usuário por ID: {}", id);
        return ResponseEntity.ok(service.findById(id));
    }
}
