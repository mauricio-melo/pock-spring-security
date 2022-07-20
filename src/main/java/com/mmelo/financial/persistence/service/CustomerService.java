package com.mmelo.financial.persistence.service;

import com.mmelo.financial.persistence.entity.Customer;
import com.mmelo.financial.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {

    private final CustomerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));
    }

    public Optional<Customer> findById(final Long id) {
        return repository.findById(id);
    }

    public Optional<Customer> findByUsernameIgnoreCase(final String userName) {
        return repository.findByUsernameIgnoreCase(userName);
    }
}