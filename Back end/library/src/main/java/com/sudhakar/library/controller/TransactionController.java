package com.sudhakar.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sudhakar.library.entity.Transaction;
import com.sudhakar.library.entity.TransactionStatus;
import com.sudhakar.library.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/user/{usernameOrEmail}")
    public ResponseEntity<List<Transaction>> getTransactionsByUsernameOrEmail(@PathVariable String usernameOrEmail) {
        return transactionService.getTransactionsByUsernameOrEmail(usernameOrEmail);
    }

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @PutMapping("/return/{usernameOrEmail}/{bookId}")
    public ResponseEntity<Transaction> returnBook(@PathVariable String usernameOrEmail, @PathVariable String bookId) {
        return transactionService.returnBook(usernameOrEmail, bookId);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Transaction>> getTransactionsByStatus(@PathVariable TransactionStatus status) {
        return transactionService.getTransactionsByStatus(status);
    }

    @GetMapping("/user/status/{usernameOrEmail}/{status}")
    public ResponseEntity<List<Transaction>> getTransactionsByUsernameOrEmailWithStatus(
            @PathVariable String usernameOrEmail, @PathVariable TransactionStatus status) {
        return transactionService.getTransactionByUsernameOrEmailWithStatus(usernameOrEmail, status);
    }
}
