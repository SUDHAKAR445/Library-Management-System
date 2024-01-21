package com.sudhakar.library.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sudhakar.library.entity.Transaction;
import com.sudhakar.library.entity.TransactionStatus;

public interface TransactionService {

    ResponseEntity<List<Transaction>> getAllTransactions();

    ResponseEntity<List<Transaction>> getTransactionsByUsernameOrEmail(String usernameOrEmail);

    ResponseEntity<Transaction> createTransaction(Transaction transaction);

    ResponseEntity<Transaction> returnBook(String usernameOrEmail, String bookId);

    ResponseEntity<List<Transaction>> getTransactionsByStatus(TransactionStatus status);

    ResponseEntity<List<Transaction>> getTransactionByUsernameOrEmailWithStatus(String usernameOrEmail,
            TransactionStatus status);

}
