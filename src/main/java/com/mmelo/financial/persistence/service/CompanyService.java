package com.mmelo.financial.persistence.service;

import com.mmelo.financial.domain.CompanyDTO;
import com.mmelo.financial.persistence.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;
    private final ModelMapper modelMapper;

    public CompanyDTO findById(final Long id) {
        return modelMapper.map(repository.findById(id)
                .orElseThrow(RuntimeException::new), CompanyDTO.class);
    }

    public List<CompanyDTO> findAll() {
        return repository.findAll().stream()
                .map(bet -> modelMapper.map(bet, CompanyDTO.class))
                .collect(Collectors.toList());
    }
}