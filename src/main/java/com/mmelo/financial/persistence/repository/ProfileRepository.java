package com.mmelo.financial.persistence.repository;

import com.mmelo.financial.persistence.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByName(final String name);
}