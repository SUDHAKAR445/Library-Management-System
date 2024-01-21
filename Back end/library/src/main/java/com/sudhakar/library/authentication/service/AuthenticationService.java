package com.sudhakar.library.authentication.service;

import org.springframework.http.ResponseEntity;

import com.sudhakar.library.authentication.dto.AuthenticationRequest;
import com.sudhakar.library.authentication.dto.AuthenticationResponse;
import com.sudhakar.library.authentication.dto.RegisterRequest;

public interface AuthenticationService {
    
    ResponseEntity<AuthenticationResponse> register(RegisterRequest request);

    ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);
}
