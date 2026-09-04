package com.bio7.user.dto.response;

import com.bio7.user.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;

    private String username;

    private String email;

    private Role role;

    private boolean enabled;
}
