package com.mmelo.financial.persistence.repository;

import com.mmelo.financial.persistence.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}