package com.api.forumHub.infra.security.auth;

import com.api.forumHub.domain.usuario.Usuario;
import com.api.forumHub.infra.exception.NotFoundException;
import com.api.forumHub.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        if(token != null) {
            Long subject = tokenService.getSubject(token);
            Usuario usuario = repository.findById(subject)
                    .orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));
            var auth = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(token != null && !token.isEmpty()) {
            return token.replace("Bearer ","");
        }

        return null;
    }
}
