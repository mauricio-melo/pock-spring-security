package com.mmelo.financial.persistence.service;

import com.mmelo.financial.persistence.entity.Profile;
import com.mmelo.financial.persistence.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repository;

    public Profile findByName(final String name) {
        return repository.findByName(name)
                .orElseThrow(RuntimeException::new);
    }
}