package com.mmelo.financial.security;

import com.mmelo.financial.domain.CustomerDTO;
import com.mmelo.financial.domain.TokenDTO;
import com.mmelo.financial.persistence.service.CustomerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {
    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    private final CustomerService customerService;

    public TokenDTO generateToken(final Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        final CustomerDTO customerDTO = customerService.findByUsernameIgnoreCase(user.getUsername()).get();
        return TokenDTO.builder()
                .username(customerDTO.getUsername())
                .name(customerDTO.getName())
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .type("Bearer")
                .token(Jwts.builder().setIssuer("Financial")
                        .setSubject(customerDTO.getId().toString())
                        .claim("username", customerDTO.getUsername())
                        .claim("name", customerDTO.getName())
                        .claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(new Date().getTime() + Long.parseLong(expiration)))
                        .signWith(SignatureAlgorithm.HS256, secret).compact())
                .build();
    }

    public boolean isTokenValid(final String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getCustomerIdByToken(final String token) {
        final Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.parseLong(body.getSubject());
    }
}