package com.bio7.auth.service;

import com.bio7.user.dto.request.CreateUserRequestDTO;
import com.bio7.user.dto.response.UserResponseDTO;
import com.bio7.user.entity.Role;
import com.bio7.user.entity.User;
import com.bio7.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bio7.user.mapper.UserMapper;
@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder ;
    private final UserMapper userMapper ;


    public UserResponseDTO Register(CreateUserRequestDTO request){

        if(userRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder().
                userName(request.getUserName()).
                email(request.getEmail()).
                passwordHash(passwordEncoder.encode(request.getPassword())).
                enabled(true).
                role(Role.CLIENT).
                build();

        User savedUser = userRepository.save(user);

        return userMapper.toResponseDTO(savedUser);


    }





}
