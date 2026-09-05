package com.bio7.auth.controller;

import com.bio7.auth.service.AuthService;
import com.bio7.user.dto.request.CreateUserRequestDTO;
import com.bio7.user.dto.response.UserResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

       private final AuthService authService ;

       @PostMapping("/register")
       public ResponseEntity<UserResponseDTO> register(
               @Valid @RequestBody
               CreateUserRequestDTO request){
           UserResponseDTO userResponseDTO= authService.Register(request);

           return ResponseEntity.status(HttpStatus.CREATED)
                   .body(userResponseDTO);

       }

}
