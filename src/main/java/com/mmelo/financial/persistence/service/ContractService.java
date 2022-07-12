package com.mmelo.financial.persistence.service;

import com.mmelo.financial.domain.ContractDTO;
import com.mmelo.financial.persistence.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository repository;
    private final ModelMapper modelMapper;

    public ContractDTO findById(final Long id) {
        return modelMapper.map(repository.findById(id)
                .orElseThrow(RuntimeException::new), ContractDTO.class);
    }
}