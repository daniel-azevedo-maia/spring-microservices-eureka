package com.ms.user.mapper;

import com.ms.user.application.dto.UserDTO;
import com.ms.user.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
    }

    public UserDTO toDTO(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .build();
    }
}
