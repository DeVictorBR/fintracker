package com.victorbarbosa.fintracker.config;

import com.victorbarbosa.fintracker.entity.Authority;
import com.victorbarbosa.fintracker.entity.Role;
import com.victorbarbosa.fintracker.repository.AuthorityRepository;
import com.victorbarbosa.fintracker.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, AuthorityRepository authorityRepository) {
        return args -> {
            if (!roleRepository.findAll().isEmpty()) return;

            var authorities = new HashSet<Authority>();

            authorities.add(new Authority(null, Authority.Values.TRANSACTION_CREATE));
            authorities.add(new Authority(null, Authority.Values.TRANSACTION_READ));
            authorities.add(new Authority(null, Authority.Values.TRANSACTION_UPDATE));
            authorities.add(new Authority(null, Authority.Values.TRANSACTION_DELETE));

            authorities.add(new Authority(null, Authority.Values.USER_CREATE));
            authorities.add(new Authority(null, Authority.Values.USER_READ));
            authorities.add(new Authority(null, Authority.Values.USER_UPDATE));
            authorities.add(new Authority(null, Authority.Values.USER_DELETE));

            authorities.add(new Authority(null, Authority.Values.CATEGORY_CREATE));
            authorities.add(new Authority(null, Authority.Values.CATEGORY_READ));
            authorities.add(new Authority(null, Authority.Values.CATEGORY_UPDATE));
            authorities.add(new Authority(null, Authority.Values.CATEGORY_DELETE));

            authorities.add(new Authority(null, Authority.Values.SETTINGS_UPDATE));

            authorityRepository.saveAll(authorities);

            var allAuthorities = new HashSet<>(authorities);

            var userAuthorities = new HashSet<Authority>();
            authorities.forEach(a -> {
                if (a.getName().endsWith("read") || a.getName().startsWith("transaction")) {
                    userAuthorities.add(a);
                }
            });

            var userRole = new Role("USER", userAuthorities);
            var adminRole = new Role("ADMIN", allAuthorities);

            roleRepository.save(userRole);
            roleRepository.save(adminRole);

            System.out.println("✅ Authorities e Roles inicializadas!");
        };
    }
}
