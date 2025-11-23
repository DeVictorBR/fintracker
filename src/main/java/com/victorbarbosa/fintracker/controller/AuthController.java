package com.victorbarbosa.fintracker.controller;

import com.victorbarbosa.fintracker.controller.dto.SignupCreateRequest;
import com.victorbarbosa.fintracker.controller.dto.SignupCreateResponse;
import com.victorbarbosa.fintracker.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupCreateResponse> signup(@RequestBody @Valid SignupCreateRequest req) {
        authService.signup(req);
        return ResponseEntity.ok(new SignupCreateResponse("User created successfully!"));
    }
}
