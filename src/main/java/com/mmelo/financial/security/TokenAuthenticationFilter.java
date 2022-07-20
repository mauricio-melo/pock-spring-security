package com.mmelo.financial.security;

import com.mmelo.financial.persistence.service.CustomerService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final CustomerService customerService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String token = getTokenFromHeader(request);
        boolean tokenValid = tokenService.isTokenValid(token);
        if (tokenValid) {
            this.authenticate(token);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticate(String token) {
        final Claims claims = tokenService.getTokenDetails(token);
        customerService.findById(Long.parseLong(claims.getSubject()))
                .ifPresent(customer -> SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(customer, null,
                        tokenService.getAuthoritiesByToken(claims))));
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }

}