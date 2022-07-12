package com.mmelo.financial.persistence.repository;

import com.mmelo.financial.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(final String name);
}