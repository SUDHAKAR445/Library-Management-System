package com.sudhakar.library.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sudhakar.library.entity.Author;
import com.sudhakar.library.entity.Book;
import com.sudhakar.library.exception.DuplicateAuthorException;
import com.sudhakar.library.repository.AuthorRepository;
import com.sudhakar.library.repository.BookRepository;
import com.sudhakar.library.service.AuthorService;

@Service
public class AuthorServiceImplementation implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public ResponseEntity<List<Author>> getAllAuthors() {
        try {
            List<Author> authors = authorRepository.findAll();
            return new ResponseEntity<>(authors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Author> createAuthor(Author author) {
        try {
            if (authorRepository.existsByAuthorId(author.getAuthorId())) {
                throw new DuplicateAuthorException("Author Id already exists.");
            }

            Author savedAuthor = authorRepository.save(author);
            return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
        } catch (DuplicateAuthorException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Author> updateAuthorByAuthorId(String authorId, Author updatedAuthor) {
        try {
            if (updatedAuthor == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Optional<Author> optionalAuthor = authorRepository.findByAuthorId(authorId);

            if (optionalAuthor.isPresent()) {

                Author existingAuthor = optionalAuthor.get();

                existingAuthor.setAuthorId(updatedAuthor.getAuthorId() != null ? updatedAuthor.getAuthorId()
                        : existingAuthor.getAuthorId());
                existingAuthor.setAuthorName(
                        updatedAuthor.getAuthorName() != null ? updatedAuthor.getAuthorName()
                                : existingAuthor.getAuthorName());
                Author updated = authorRepository.save(existingAuthor);

                List<Book> books = bookRepository.findByAuthor(existingAuthor);

                for (Book book : books) {
                    book.setAuthor(updatedAuthor);
                    bookRepository.save(book);
                }

                return new ResponseEntity<>(updated, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> deleteAuthorByAuthorId(String authorId) {
        try {
            Optional<Author> optionalAuthor = authorRepository.findByAuthorId(authorId);
            if (optionalAuthor.isPresent()) {
                Author author = optionalAuthor.get();
                List<Book> books = bookRepository.findByAuthor(optionalAuthor.get());
                for (Book book : books) {
                    bookRepository.delete(book);
                }

                authorRepository.delete(author);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Author> getAuthorById(String authorId) {
        try {
            Optional<Author> optionalAuthor = authorRepository.findByAuthorId(authorId);
            if (optionalAuthor.isPresent()) {
                Author author = optionalAuthor.get();
                return new ResponseEntity<>(author, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
