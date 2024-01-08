package com.prueba.demo.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        authorities.forEach(authority -> {
            try {
                if (authority.equals(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                    redirectStrategy.sendRedirect(request, response, "/admin");
                } else if (authority.equals(new SimpleGrantedAuthority("ROLE_USER"))) {
                    redirectStrategy.sendRedirect(request, response, "/user");
                } else {
                    throw new IllegalStateException();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}