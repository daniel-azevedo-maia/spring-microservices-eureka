package com.ms.user.application.service;

import com.ms.user.application.dto.UserDTO;
import com.ms.user.domain.entity.User;
import com.ms.user.domain.exception.EmailAlreadyExistsException;
import com.ms.user.domain.exception.UserNotFoundException;
import com.ms.user.mapper.UserMapper;
import com.ms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserDTO createUser(UserDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("E-mail já cadastrado: " + dto.getEmail());
        }

        User savedUser = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(savedUser);
    }

    public List<UserDTO> listAllUsers() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));
        return mapper.toDTO(user);
    }
}
