package com.victorbarbosa.fintracker.controller;

import com.victorbarbosa.fintracker.controller.dto.MeDto;
import com.victorbarbosa.fintracker.controller.dto.SignupCreateRequest;
import com.victorbarbosa.fintracker.controller.dto.SignupCreateResponse;
import com.victorbarbosa.fintracker.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public MeDto me(Authentication auth) {
        var authorities = auth
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return new MeDto(auth.getName(), authorities);
    }
}
