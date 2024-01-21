package com.sudhakar.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sudhakar.library.entity.Role;
import com.sudhakar.library.entity.User;
import com.sudhakar.library.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{usernameOrEmail}")
    public ResponseEntity<User> getUserByUsernameOrEmail(@PathVariable String usernameOrEmail) {
        return userService.getUserByUsernameOrEmail(usernameOrEmail);
    }

    @PutMapping("/update/{usernameOrEmail}")
    public ResponseEntity<User> updateUserByUsernameOrEmail(@PathVariable String usernameOrEmail,
            @RequestBody User updatedUser) {
        return userService.updateUserByUsernameOrEmail(usernameOrEmail, updatedUser);
    }

    @DeleteMapping("/delete/{usernameOrEmail}")
    public ResponseEntity<Void> deleteUserByUsernameOrEmail(@PathVariable String usernameOrEmail) {
        return userService.deleteUserByUsernameOrEmail(usernameOrEmail);
    }

    @GetMapping("/by-role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable Role role) {
        return userService.getUsersByRole(role);
    }
}