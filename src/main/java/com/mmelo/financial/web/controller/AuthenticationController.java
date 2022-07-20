package com.mmelo.financial.web.controller;

import com.mmelo.financial.security.TokenService;
import com.mmelo.financial.web.controller.request.LoginDTO;
import com.mmelo.financial.web.controller.response.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(AuthenticationController.AUTH_ENDPOINT)
public class AuthenticationController {

    public static final String AUTH_ENDPOINT = "/auth";

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> auth(@RequestBody @Validated final LoginDTO loginDTO) {
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        return ResponseEntity.ok(tokenService.generateToken(authentication));
    }

}