package com.sudhakar.library.authentication.dto;

import com.sudhakar.library.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

    private String username;

    private String password;
}
