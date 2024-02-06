package com.sudhakar.library.service.implementation;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sudhakar.library.entity.Book;
import com.sudhakar.library.entity.Transaction;
import com.sudhakar.library.entity.TransactionStatus;
import com.sudhakar.library.entity.User;
import com.sudhakar.library.repository.BookRepository;
import com.sudhakar.library.repository.TransactionRepository;
import com.sudhakar.library.repository.UserRepository;
import com.sudhakar.library.service.TransactionService;

@Service
public class TransactionServiceImplementation implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    public ResponseEntity<List<Transaction>> getAllTransactions() {
        try {
            List<Transaction> transactions = transactionRepository.findAll();
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Transaction>> getTransactionsByUsernameOrEmail(String usernameOrEmail) {
        try {
            List<Transaction> transactions = transactionRepository.findByUserUsernameOrUserEmail(usernameOrEmail,
                    usernameOrEmail);
            if (!transactions.isEmpty()) {
                return new ResponseEntity<>(transactions, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Transaction> createTransaction(Transaction transaction) {
        try {
            Optional<Book> optionalBook = bookRepository.findByBookId(transaction.getBook().getBookId());
            Optional<User> optionalUser = userRepository.findByUsername(transaction.getUser().getUsername());

            if (optionalBook.isPresent() && optionalUser.isPresent()) {
                Book book = optionalBook.get();
                User user = optionalUser.get();

                Optional<Transaction> existingTransaction = transactionRepository
                        .findByUserAndBookAndTransactionStatus(user, book, TransactionStatus.BORROW);

                if (existingTransaction.isPresent()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                if (book.getAvailableQuantity() <= 0) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                transaction.setBook(book);
                transaction.setUser(user);
                transaction.setBorrowDate(new Date());
                transaction.setTransactionStatus(TransactionStatus.BORROW);

                Transaction savedTransaction = transactionRepository.save(transaction);

                book.setAvailableQuantity(book.getAvailableQuantity() - 1);
                bookRepository.save(book);

                return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Create the multiple transaction at a time
    //i pass the each and every time i pass the username because i need to modify the entire code a list of book for that purpose i create the list of transaction each contain username
    public ResponseEntity<List<Transaction>> createTransactions(List<Transaction> transactions) {
    try {
        List<Transaction> savedTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            Optional<Book> optionalBook = bookRepository.findByBookId(transaction.getBook().getBookId());
            Optional<User> optionalUser = userRepository.findByUsername(transaction.getUser().getUsername());

            if (optionalBook.isPresent() && optionalUser.isPresent()) {
                Book book = optionalBook.get();
                User user = optionalUser.get();

                Optional<Transaction> existingTransaction = transactionRepository
                        .findByUserAndBookAndTransactionStatus(user, book, TransactionStatus.BORROW);

                if (existingTransaction.isPresent()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                if (book.getAvailableQuantity() <= 0) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                transaction.setBook(book);
                transaction.setUser(user);
                transaction.setBorrowDate(new Date());
                transaction.setTransactionStatus(TransactionStatus.BORROW);

                Transaction savedTransaction = transactionRepository.save(transaction);
                savedTransactions.add(savedTransaction);

                book.setAvailableQuantity(book.getAvailableQuantity() - 1);
                bookRepository.save(book);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(savedTransactions, HttpStatus.CREATED);

    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    public ResponseEntity<Transaction> returnBook(String usernameOrEmail, String bookId) {
        try {
            Optional<User> optionalUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
            Optional<Book> optionalBook = bookRepository.findByBookId(bookId);

            if (optionalUser.isPresent() && optionalBook.isPresent()) {
                Book book = optionalBook.get();
                User user = optionalUser.get();

                Optional<Transaction> borrowed = transactionRepository
                        .findByUserAndBookAndTransactionStatus(user, book, TransactionStatus.BORROW);

                if (borrowed.isPresent()) {
                    Transaction borrowedTransaction = borrowed.get();
                    borrowedTransaction.setReturnDate(new Date(System.currentTimeMillis()));
                    borrowedTransaction.setTransactionStatus(TransactionStatus.RETURN);

                    // Calculate the fine amount
                    long duration = Duration
                            .between(borrowedTransaction.getBorrowDate().toInstant(), new Date().toInstant()).toDays();

                    System.out.println(duration);
                    int fineDays = 1;

                    if (fineDays < duration) {
                        long daysDifference = duration - fineDays;
                        BigDecimal fineAmount = new BigDecimal("100.00").multiply(BigDecimal.valueOf(daysDifference));
                        borrowedTransaction.setFineAmount(fineAmount);
                    }
                    book.setAvailableQuantity(book.getAvailableQuantity() + 1);
                    bookRepository.save(book);

                    Transaction savedTransaction = transactionRepository.save(borrowedTransaction);
                    return new ResponseEntity<>(savedTransaction, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Transaction>> getTransactionsByStatus(TransactionStatus status) {
        try {
            List<Transaction> transactions = transactionRepository.findByTransactionStatus(status);

            if (!transactions.isEmpty()) {
                return new ResponseEntity<>(transactions, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Transaction>> getTransactionByUsernameOrEmailWithStatus(String usernameOrEmail,
            TransactionStatus status) {
        try {
            List<Transaction> transactions = transactionRepository
                    .findByUserUsernameOrUserEmailAndTransactionStatus(usernameOrEmail, usernameOrEmail, status);

            if (!transactions.isEmpty()) {
                return new ResponseEntity<>(transactions, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
