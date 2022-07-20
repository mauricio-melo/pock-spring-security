package com.mmelo.financial.persistence.service;

import com.mmelo.financial.persistence.entity.Company;
import com.mmelo.financial.persistence.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;

    public Company findById(final Long id) {
        return repository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    public List<Company> findAll() {
        return repository.findAll();
    }
}