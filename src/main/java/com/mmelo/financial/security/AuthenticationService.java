package com.mmelo.financial.security;

import com.mmelo.financial.domain.CustomerDTO;
import com.mmelo.financial.domain.ProfileRoleDTO;
import com.mmelo.financial.persistence.service.ProfileRoleService;
import com.mmelo.financial.persistence.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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
        final List<ProfileRoleDTO> profileRoles = profileRoleService.findByUsername(username);
        return createUserDetails(profileRoles, customerDTO.get());
    }

    public CustomerDTO getUserAuthenticate() {
        return (CustomerDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private User createUserDetails(final List<ProfileRoleDTO> profileRoles, final CustomerDTO customerDTO) {
        return new User(customerDTO.getUsername(),
                customerDTO.getPassword(),
                AuthorityUtils.createAuthorityList(getAuthorities(profileRoles)));
    }

    private String[] getAuthorities(final List<ProfileRoleDTO> customerRoles) {
        return customerRoles
                .stream().map(customerRole -> customerRole.getRole().getName())
                .toArray(String[]::new);
    }
}