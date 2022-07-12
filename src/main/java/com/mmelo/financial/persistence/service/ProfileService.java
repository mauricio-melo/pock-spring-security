package com.mmelo.financial.persistence.service;

import com.mmelo.financial.domain.ProfileDTO;
import com.mmelo.financial.persistence.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repository;
    private final ModelMapper modelMapper;

    public ProfileDTO findByName(final String name) {
        return modelMapper.map(repository.findByName(name)
                .orElseThrow(RuntimeException::new), ProfileDTO.class);
    }
}