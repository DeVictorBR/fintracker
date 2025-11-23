package com.victorbarbosa.fintracker.repository;

import com.victorbarbosa.fintracker.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
