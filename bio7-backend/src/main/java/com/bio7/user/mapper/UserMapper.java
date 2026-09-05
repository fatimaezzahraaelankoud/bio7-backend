package com.bio7.user.mapper;

import com.bio7.user.dto.request.CreateUserRequestDTO;
import com.bio7.user.dto.response.UserResponseDTO;
import com.bio7.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toResponseDTO(User user) {

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setEnabled(user.isEnabled());

        return dto;
    }

}
