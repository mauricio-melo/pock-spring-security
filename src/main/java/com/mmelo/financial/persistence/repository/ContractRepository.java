package com.mmelo.financial.persistence.repository;

import com.mmelo.financial.persistence.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}