package com.sudhakar.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sudhakar.library.entity.Book;
import com.sudhakar.library.entity.Transaction;
import com.sudhakar.library.entity.TransactionStatus;
import com.sudhakar.library.entity.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

        List<Transaction> findByTransactionStatus(TransactionStatus status);

        Transaction findByUserUsernameOrUserEmailAndBookBookIdAndTransactionStatus(String username,
                        String email, String bookId, TransactionStatus status);

        List<Transaction> findByUserUsernameOrUserEmailAndTransactionStatus(String username, String email,
                        TransactionStatus status);

        List<Transaction> findByUserUsernameOrUserEmail(String username, String email);

        Optional<Transaction> findByUserAndBookAndTransactionStatus(User user, Book book, TransactionStatus borrow);

        List<Transaction> findByUserUsernameOrUserEmailAndReturnDateIsNull(String username, String email);

        @Query("SELECT t FROM Transaction t WHERE (t.user.username = :username OR t.user.email = :email) AND t.returnDate IS NULL")
        List<Transaction> findActiveBorrowTransactions(@Param("username") String username,
                        @Param("email") String email);

}
