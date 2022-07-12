package com.mmelo.financial.persistence.service;

import com.mmelo.financial.domain.CustomerDTO;
import com.mmelo.financial.persistence.entity.Company;
import com.mmelo.financial.persistence.entity.Customer;
import com.mmelo.financial.persistence.entity.Profile;
import com.mmelo.financial.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final ProfileService profileService;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    public void save(final String name, final String username, final String profile, final Long companyId) {
        repository.save(Customer.builder()
                .username(username)
                .name(name)
                .password(new BCryptPasswordEncoder().encode("123_trocar_senha"))
                .profile(modelMapper.map(profileService.findByName(profile), Profile.class))
                .company(modelMapper.map(companyService.findById(companyId), Company.class))
                .build());
    }

    public Optional<CustomerDTO> findById(final Long id) {
        return repository.findById(id).map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }

    public Optional<CustomerDTO> findByUsernameIgnoreCase(final String userName) {
        return repository.findByUsernameIgnoreCase(userName)
                .map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }

}