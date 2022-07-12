package com.mmelo.financial.persistence.repository;

import com.mmelo.financial.persistence.entity.ProfileRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRoleRepository extends JpaRepository<ProfileRole, Long> {
    List<ProfileRole> findByProfile_customer_usernameIgnoreCase(final String username);
}