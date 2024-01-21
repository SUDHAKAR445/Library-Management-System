package com.sudhakar.library.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.sudhakar.library.authentication.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;
        private final LogoutHandler logoutHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http.csrf(csrf -> csrf
                                .disable())
                                .authorizeHttpRequests(requests -> requests
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers("/api/authors/delete/**",
                                                                "/api/books/delete/**",
                                                                "/api/genres/delete/**",
                                                                "/api/publishers/delete/**",
                                                                "/api/users/all",
                                                                "/api/users/**",
                                                                "/api/users/delete/**",
                                                                "/api/users/by-role/**")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers("/api/authors/create",
                                                                "/api/authors/update/**",
                                                                "/api/books/create",
                                                                "/api/books/update/**",
                                                                "/api/genres/create",
                                                                "/api/genres/update/**",
                                                                "/api/publishers/create",
                                                                "/api/publishers/update/**",
                                                                "/api/transactions/all",
                                                                "/api/transactions/user/**",
                                                                "/api/transactions/create",
                                                                "/api/transactions/return/**",
                                                                "/api/transactions/status/**",
                                                                "/api/transactions/user/status/**")
                                                .hasAnyAuthority("ADMIN", "LIBRARIAN")
                                                .anyRequest()
                                                .authenticated())
                                .sessionManagement(management -> management
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                .logout(logout -> logout
                                                .logoutUrl("api/auth/logout")
                                                .addLogoutHandler(logoutHandler)
                                                .logoutSuccessHandler((request, response,
                                                                authentication) -> SecurityContextHolder
                                                                                .clearContext()));

                return http.build();
        }
}
