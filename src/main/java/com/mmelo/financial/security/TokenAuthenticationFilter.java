package com.mmelo.financial.security;

import com.mmelo.financial.persistence.entity.ProfileRole;
import com.mmelo.financial.persistence.service.ProfileRoleService;
import com.mmelo.financial.persistence.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final TokenService tokenService;
	private final CustomerService customerService;
	private final ProfileRoleService profileRoleService;

	private final ModelMapper modelMapper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String tokenFromHeader = getTokenFromHeader(request);
		boolean tokenValid = tokenService.isTokenValid(tokenFromHeader);
		if(tokenValid) {
			this.authenticate(tokenFromHeader);
		}
		filterChain.doFilter(request, response);
	}

	private void authenticate(String tokenFromHeader) {
		customerService.findById(tokenService.getCustomerIdByToken(tokenFromHeader))
				.ifPresent(customerDTO -> SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(customerDTO, null,
						profileRoleService.findByUsername(customerDTO.getUsername())
								.stream()
								.map(customerRoleDTO -> modelMapper.map(customerRoleDTO, ProfileRole.class))
								.collect(Collectors.toList()))));
	}

	public String getTokenFromHeader(HttpServletRequest request) {
		final String token = request.getHeader("Authorization");
		if(token == null || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7);
	}

}