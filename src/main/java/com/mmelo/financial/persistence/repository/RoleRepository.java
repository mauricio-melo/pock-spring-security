package com.mmelo.financial.persistence.repository;

import com.mmelo.financial.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}