package com.victorbarbosa.fintracker.service;

import com.victorbarbosa.fintracker.controller.dto.SignupCreateRequest;
import com.victorbarbosa.fintracker.entity.User;
import com.victorbarbosa.fintracker.repository.RoleRepository;
import com.victorbarbosa.fintracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(SignupCreateRequest req) {
        if (userRepository.findByEmail(req.email()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("USER role not found"));

        var user = new User(req.username(), req.email(), passwordEncoder.encode(req.password()), Set.of(userRole));
        userRepository.save(user);
    }
}
