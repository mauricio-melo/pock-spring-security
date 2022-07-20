package com.mmelo.financial.security;

import com.mmelo.financial.persistence.entity.Customer;
import com.mmelo.financial.web.controller.response.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {
    @Value("${jwt.key}")
    private String key;

    public TokenDTO generateToken(final Authentication authentication) {
        final Customer customer = (Customer) authentication.getPrincipal();
        return TokenDTO.builder()
                .token(Jwts.builder().setIssuer("Financial")
                        .setSubject(customer.getId().toString())
                        .claim("username", customer.getUsername())
                        .claim("name", customer.getName())
                        .claim("role", customer.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
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
    public Claims getTokenDetails(final String token) {
       return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token).getBody();
    }

    public Collection<? extends GrantedAuthority> getAuthoritiesByToken(final Claims claims) {
        List<String> role = (List<String>) claims.get("role");
        return role.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}