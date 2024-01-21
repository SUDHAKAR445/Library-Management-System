package com.sudhakar.library.authentication.service.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sudhakar.library.authentication.dto.AuthenticationRequest;
import com.sudhakar.library.authentication.dto.AuthenticationResponse;
import com.sudhakar.library.authentication.dto.RegisterRequest;
import com.sudhakar.library.authentication.model.Token;
import com.sudhakar.library.authentication.model.TokenType;
import com.sudhakar.library.authentication.repository.TokenRepository;
import com.sudhakar.library.authentication.service.AuthenticationService;
import com.sudhakar.library.authentication.service.JwtService;
import com.sudhakar.library.entity.Role;
import com.sudhakar.library.entity.User;
import com.sudhakar.library.exception.DuplicateUserException;
import com.sudhakar.library.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AuthenticationServiceImplementation implements AuthenticationService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        @Autowired
        TokenRepository tokenRepository;

        public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
                User user = User.builder()
                                .firstName(request.getFirstName())
                                .lastName(request.getLastName())
                                .email(request.getEmail())
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .build();

                if (user.getEmail().endsWith("admin@gmail.com") && user.getUsername().endsWith("_admin")) {
                        user.setRole(Role.ADMIN);
                } else if (user.getEmail().endsWith("librarian@gmail.com")
                                && user.getUsername().endsWith("_librarian")) {
                        user.setRole(Role.LIBRARIAN);
                } else {
                        user.setRole(Role.MEMBER);
                }
                User savedUser;
                try {
                        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
                                throw new DuplicateUserException("Username or email already exists.");
                        }
                        savedUser = userRepository.save(user);
                } catch (DuplicateUserException e) {
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                } catch (Exception e) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("Authorities", savedUser.getAuthorities());
                String jwtToken = jwtService.generateToken(extraClaims, savedUser);

                revokeAllUserTokens(savedUser);
                saveUserToken(savedUser, jwtToken);

                return new ResponseEntity<>(AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build(), HttpStatus.OK);
        }

        public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {

                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getUsername(),
                                                request.getPassword()));

                User user = userRepository.findByUsername(request.getUsername())
                                .orElseThrow(() -> new UsernameNotFoundException("Username or Password invalid"));

                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("Authorities", user.getAuthorities());
                String jwtToken = jwtService.generateToken(extraClaims, user);
                revokeAllUserTokens(user);
                saveUserToken(user, jwtToken);
                return new ResponseEntity<>(AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build(), HttpStatus.OK);
        }

        private void saveUserToken(User user, String jwtToken) {

                Token token = Token.builder()
                                .user(user)
                                .token(jwtToken)
                                .tokenType(TokenType.BEARER)
                                .expired(false)
                                .revoked(false)
                                .build();

                tokenRepository.save(token);
        }

        private void revokeAllUserTokens(User user) {

                List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
                if (validUserTokens.isEmpty()) {
                        return;
                }
                validUserTokens.forEach((token -> {
                        token.setExpired(true);
                        token.setRevoked(true);
                }));
                tokenRepository.saveAll(validUserTokens);
        }
}
