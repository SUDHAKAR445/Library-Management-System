package com.sudhakar.library.service.implementation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sudhakar.library.authentication.model.Token;
import com.sudhakar.library.authentication.repository.TokenRepository;
import com.sudhakar.library.entity.Role;
import com.sudhakar.library.entity.Transaction;
import com.sudhakar.library.entity.User;
import com.sudhakar.library.repository.TransactionRepository;
import com.sudhakar.library.repository.UserRepository;
import com.sudhakar.library.service.TransactionService;
import com.sudhakar.library.service.UserService;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TransactionService transactionService;

    @Autowired
    TokenRepository tokenRepository;

    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<User> getUserByUsernameOrEmail(String usernameOrEmail) {
        try {
            Optional<User> optionalUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
            return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<User> updateUserByUsernameOrEmail(String usernameOrEmail, User updateUser) {
        try {
            Optional<User> optionalUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

            if (optionalUser.isPresent()) {
                User existingMember = optionalUser.get();

                existingMember.setFirstName(updateUser.getFirstName() != null ? updateUser.getFirstName()
                        : existingMember.getFirstName());
                existingMember.setLastName(
                        updateUser.getLastName() != null ? updateUser.getLastName() : existingMember.getLastName());
                existingMember.setEmail(
                        updateUser.getEmail() != null ? updateUser.getEmail() : existingMember.getEmail());
                existingMember.setUsername(
                        updateUser.getUsername() != null ? updateUser.getUsername() : existingMember.getUsername());
                existingMember.setPassword(
                        updateUser.getPassword() != null ? passwordEncoder.encode(updateUser.getPassword())
                                : existingMember.getPassword());
                existingMember.setRole(existingMember.getRole());

                User savedMember = userRepository.save(existingMember);
                return new ResponseEntity<>(savedMember, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> deleteUserByUsernameOrEmail(String usernameOrEmail) {
        try {
            Optional<User> optionalUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                List<Transaction> activeTransactions = transactionRepository
                        .findActiveBorrowTransactions(user.getUsername(), user.getEmail());

                if (!activeTransactions.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                List<Transaction> transactions = transactionRepository.findByUserUsernameOrUserEmail(user.getUsername(),
                        user.getEmail());
                if (!transactions.isEmpty())
                    transactions.forEach((transaction) -> transactionRepository.delete(transaction));

                List<Token> tokens = tokenRepository.findByUserUsername(user.getUsername());
                tokens.forEach((token) -> tokenRepository.delete(token));

                userRepository.delete(user);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<User>> getUsersByRole(Role role) {
        try {
            if (role != null && Arrays.asList(Role.values()).contains(role)) {
                List<User> users = userRepository.findByRole(role);
                return new ResponseEntity<>(users, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}