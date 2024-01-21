package com.sudhakar.library.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sudhakar.library.entity.Role;
import com.sudhakar.library.entity.User;

public interface UserService {

    ResponseEntity<List<User>> getAllUsers();

    ResponseEntity<User> getUserByUsernameOrEmail(String usernameOrEmail);

    ResponseEntity<User> updateUserByUsernameOrEmail(String usernameOrEmail, User user);

    ResponseEntity<Void> deleteUserByUsernameOrEmail(String usernameOrEmail);

    ResponseEntity<List<User>> getUsersByRole(Role role);
}