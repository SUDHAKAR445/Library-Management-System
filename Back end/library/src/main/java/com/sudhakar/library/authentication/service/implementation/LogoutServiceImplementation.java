package com.sudhakar.library.authentication.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.sudhakar.library.authentication.model.Token;
import com.sudhakar.library.authentication.repository.TokenRepository;
import com.sudhakar.library.authentication.service.LogoutService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogoutServiceImplementation implements LogoutService {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        Optional<Token> storedToken = tokenRepository.findByToken(jwt);
        if (storedToken.isPresent()) {
            Token token = storedToken.get();
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
        }

    }

}
