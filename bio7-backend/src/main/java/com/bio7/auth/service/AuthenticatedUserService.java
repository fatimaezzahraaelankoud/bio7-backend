package com.bio7.auth.service;

import com.bio7.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService {
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof User)) {

            throw new IllegalStateException("Utilisateur non authentifié");
        }

        return (User) authentication.getPrincipal();
    }
}
