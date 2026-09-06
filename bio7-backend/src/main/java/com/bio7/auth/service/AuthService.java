package com.bio7.auth.service;

import com.bio7.user.dto.request.CreateUserRequestDTO;
import com.bio7.user.dto.request.LoginRequestDTO;
import com.bio7.user.dto.response.LoginResponseDTO;
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
    private final JwtService jwtService ;


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


    public LoginResponseDTO Login(LoginRequestDTO request){

        User user = userRepository.findByEmailIgnoreCase(request.getEmail()).
                orElseThrow(()-> new RuntimeException("invalid credentials ") );

        if(!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
               throw new IllegalArgumentException("Invalid credentials");
        }

        if(!user.isEnabled()){
            throw  new IllegalArgumentException("compte inactive");

        }

        String token = jwtService.generateToken(user);
        return new LoginResponseDTO(token);
    }





}
