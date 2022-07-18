package com.mmelo.financial.security;

import com.mmelo.financial.domain.CustomerDTO;
import com.mmelo.financial.persistence.service.CustomerService;
import com.mmelo.financial.persistence.service.ProfileRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final ProfileRoleService profileRoleService;
    private final CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<CustomerDTO> customerDTO = customerService.findByUsernameIgnoreCase(username);
        if (customerDTO.isEmpty()) {
            throw new BadCredentialsException("Bad credentials");
        }
        return new User(customerDTO.get().getUsername(),
                customerDTO.get().getPassword(),
                AuthorityUtils.createAuthorityList(profileRoleService.findByUsername(username)
                        .stream().map(customerRole -> customerRole.getRole().getName())
                        .toArray(String[]::new)));
    }

}