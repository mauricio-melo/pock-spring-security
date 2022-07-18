package com.mmelo.financial.security;

import com.mmelo.financial.domain.CustomerDTO;
import com.mmelo.financial.domain.TokenDTO;
import com.mmelo.financial.persistence.service.CustomerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final CustomerService customerService;
    @Value("${jwt.key}")
    private String key;

    public TokenDTO generateToken(final Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        final CustomerDTO customerDTO = customerService.findByUsernameIgnoreCase(user.getUsername()).get();
        return TokenDTO.builder()
                .token(Jwts.builder().setIssuer("Financial")
                        .setSubject(customerDTO.getId().toString())
                        .claim("username", customerDTO.getUsername())
                        .claim("name", customerDTO.getName())
                        .claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .setIssuedAt(new Date())
                        .setExpiration(new Date((new Date()).getTime() + 600000))
                        .signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                        .compact())
                .build();
    }

    public boolean isTokenValid(final String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getCustomerIdByToken(final String token) {
        final Claims body = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token).getBody();
        return Long.parseLong(body.getSubject());
    }
}