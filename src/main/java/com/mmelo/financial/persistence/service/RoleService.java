package com.mmelo.financial.persistence.service;

import com.mmelo.financial.domain.RoleDTO;
import com.mmelo.financial.persistence.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;
    private final ModelMapper modelMapper;

    public RoleDTO findByName(final String name) {
        return modelMapper.map(repository.findByName(name)
                .orElseThrow(RuntimeException::new), RoleDTO.class);
    }
}