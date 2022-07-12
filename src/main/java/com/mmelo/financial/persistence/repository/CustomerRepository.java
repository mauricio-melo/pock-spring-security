package com.mmelo.financial.persistence.repository;

import com.mmelo.financial.persistence.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsernameIgnoreCase(final String userName);
}