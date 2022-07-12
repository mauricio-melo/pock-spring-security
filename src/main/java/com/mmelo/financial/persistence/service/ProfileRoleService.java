package com.mmelo.financial.persistence.service;

import com.mmelo.financial.domain.ProfileRoleDTO;
import com.mmelo.financial.persistence.entity.ProfileRole;
import com.mmelo.financial.persistence.repository.ProfileRoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileRoleService {
    private final ProfileRoleRepository repository;
    private final ModelMapper modelMapper;

    public ProfileRole save(final ProfileRole profileRole) {
        return repository.save(profileRole);
    }

    public List<ProfileRoleDTO> findByUsername(final String username) {
        return repository.findByProfile_customer_usernameIgnoreCase(username).stream()
                .map(customerRole -> modelMapper.map(customerRole, ProfileRoleDTO.class))
                .collect(Collectors.toList());
    }

}