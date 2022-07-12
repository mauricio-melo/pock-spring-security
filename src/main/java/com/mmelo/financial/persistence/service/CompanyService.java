package com.mmelo.financial.persistence.service;

import com.mmelo.financial.domain.CompanyDTO;
import com.mmelo.financial.domain.RoleDTO;
import com.mmelo.financial.persistence.repository.CompanyRepository;
import com.mmelo.financial.persistence.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;
    private final ModelMapper modelMapper;

    public CompanyDTO findById(final Long id) {
        return modelMapper.map(repository.findById(id)
                .orElseThrow(RuntimeException::new), CompanyDTO.class);
    }
}