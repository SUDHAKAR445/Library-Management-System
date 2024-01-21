package com.sudhakar.library.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.sudhakar.library.entity.Book;

public interface BookService {

    ResponseEntity<List<Book>> getAllBooks();

    ResponseEntity<Book> getBookByBookIdOrIsbn(String bookIdOrIsbn);

    ResponseEntity<Book> saveBook(Book book);

    ResponseEntity<Book> updateBookByBookIdOrIsbn(String bookIdOrIsbn, Book updatedBook);

    ResponseEntity<Void> deleteBookByBookIdOrIsbn(String bookIdOrIsbn);

    ResponseEntity<List<Book>> getBooksByGenre(String genre);

    ResponseEntity<List<Book>> getBooksByTitle(String title);

    ResponseEntity<List<Book>> getAllBooksByPublisherIdOrPublisherName(String publisherIdOrPublisherName);

    ResponseEntity<List<Book>> getAllBooksByAuthorIdOrAuthorName(String authorIdOrAuthorName);

    ResponseEntity<List<Book>> getAllBooksByPublicationYear(int year);

    ResponseEntity<List<Book>> getAllBooksByPublicationYearWithGenre(int year, String genre);

    ResponseEntity<List<Book>> getAllBooksByPublicationYear(int startYear, int endYear);

    ResponseEntity<List<Book>> getAllBooksByPublicationYearAndGenre(int startYear, int endYear, String genre);

    ResponseEntity<String> calculateTotalBookCount();

    ResponseEntity<String> calculateTotalBookInGenre(String genre);

    ResponseEntity<Map<String, Integer>> calculateTotalBookInGenreVice();
}
