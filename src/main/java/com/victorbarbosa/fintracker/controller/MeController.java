package com.victorbarbosa.fintracker.controller;

import com.victorbarbosa.fintracker.controller.dto.MeDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
public class MeController {

    @GetMapping
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
