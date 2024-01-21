package com.sudhakar.library.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sudhakar.library.entity.Author;

public interface AuthorService {

    ResponseEntity<List<Author>> getAllAuthors();

    ResponseEntity<Author> createAuthor(Author author);

    ResponseEntity<Author> updateAuthorByAuthorId(String authorId, Author updatedAuthor);

    ResponseEntity<Void> deleteAuthorByAuthorId(String authorId);

    ResponseEntity<Author> getAuthorById(String authorId);
}
