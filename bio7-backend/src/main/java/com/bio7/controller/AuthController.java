package com.bio7.controller;

import com.bio7.model.User;
import com.bio7.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        return userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(user.getEmail())
                        && u.getPassword().equals(user.getPassword()))
                .findFirst()
                .map(u -> "FAKE_TOKEN_" + u.getId())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}

