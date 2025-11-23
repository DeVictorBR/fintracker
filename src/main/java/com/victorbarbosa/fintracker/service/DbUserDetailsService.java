package com.victorbarbosa.fintracker.service;

import com.victorbarbosa.fintracker.entity.Authority;
import com.victorbarbosa.fintracker.entity.Role;
import com.victorbarbosa.fintracker.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class DbUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DbUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        var authz = new HashSet<GrantedAuthority>();

        user.getRoles()
                .stream()
                .map(Role::getName)
                .map(String::toUpperCase)
                .forEach(r -> authz.add(() -> "ROLE_" + r));
        
        user.getRoles()
                .stream()
                .map(Role::getAuthorities)
                .flatMap(Collection::stream)
                .map(Authority::getName)
                .map(SimpleGrantedAuthority::new)
                .forEach(authz::add);

        return new User(user.getUsername(), user.getPassword(), authz);
    }
}
