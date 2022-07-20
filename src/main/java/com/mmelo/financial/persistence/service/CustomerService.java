package com.mmelo.financial.persistence.service;

import com.mmelo.financial.persistence.entity.Customer;
import com.mmelo.financial.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {

    private final CustomerRepository repository;
    private final ProfileService profileService;
    private final CompanyService companyService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<Customer> customer = findByUsernameIgnoreCase(username);
        if (customer.isEmpty()) {
            throw new BadCredentialsException("Bad credentials");
        }
        return customer.get();
    }

    public void save(final String name, final String username, final String profile, final Long companyId) {
        repository.save(Customer.builder()
                .username(username)
                .name(name)
                .password(new BCryptPasswordEncoder().encode("123_trocar_senha"))
                .profile(profileService.findByName(profile))
                .company(companyService.findById(companyId))
                .build());
    }

    public Optional<Customer> findById(final Long id) {
        return repository.findById(id);
    }

    public Optional<Customer> findByUsernameIgnoreCase(final String userName) {
        return repository.findByUsernameIgnoreCase(userName);
    }
}