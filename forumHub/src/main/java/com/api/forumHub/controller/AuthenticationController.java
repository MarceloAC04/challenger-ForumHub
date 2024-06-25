package com.api.forumHub.controller;

import com.api.forumHub.domain.usuario.DadosLogin;
import com.api.forumHub.domain.usuario.TokenResponse;
import com.api.forumHub.infra.security.auth.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager manager;

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody DadosLogin dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.email(),dados.senha());
        Authentication auth = manager.authenticate(authToken);
        String token = tokenService.gerar(auth);
        return ResponseEntity.ok(new TokenResponse("Bearer",token));
    }
}
