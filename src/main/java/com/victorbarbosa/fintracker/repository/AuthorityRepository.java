package com.victorbarbosa.fintracker.repository;

import com.victorbarbosa.fintracker.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
