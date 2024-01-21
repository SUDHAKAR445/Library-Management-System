package com.sudhakar.library.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sudhakar.library.entity.Book;
import com.sudhakar.library.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/by-id-or-isbn/{bookIdOrIsbn}")
    public ResponseEntity<Book> getBookByIdOrIsbn(@PathVariable String bookIdOrIsbn) {
        return bookService.getBookByBookIdOrIsbn(bookIdOrIsbn);
    }

    @PostMapping("/create")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @PutMapping("/update/{bookIdOrIsbn}")
    public ResponseEntity<Book> updateBookByIdentifier(@PathVariable String bookIdOrIsbn,
            @RequestBody Book updatedBook) {
        return bookService.updateBookByBookIdOrIsbn(bookIdOrIsbn, updatedBook);
    }

    @DeleteMapping("/delete/{bookIdOrIsbn}")
    public ResponseEntity<Void> deleteBookByIdOrIsbn(@PathVariable String bookIdOrIsbn) {
        return bookService.deleteBookByBookIdOrIsbn(bookIdOrIsbn);
    }

    @GetMapping("/by-genre/{genre}")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable String genre) {
        return bookService.getBooksByGenre(genre);
    }

    @GetMapping("/by-title/{title}")
    public ResponseEntity<List<Book>> getBooksByTitle(@PathVariable String title) {
        return bookService.getBooksByTitle(title);
    }

    @GetMapping("/by-author/{authorIdOrAuthorName}")
    public ResponseEntity<List<Book>> getBooksByAuthorIdOrAuthorName(@PathVariable String authorIdOrAuthorName) {
        return bookService.getAllBooksByAuthorIdOrAuthorName(authorIdOrAuthorName);
    }

    @GetMapping("/by-publisher/{publisherIdOrPublisherName}")
    public ResponseEntity<List<Book>> getBooksByPublisherIdOrPublisherName(
            @PathVariable String publisherIdOrPublisherName) {
        return bookService.getAllBooksByPublisherIdOrPublisherName(publisherIdOrPublisherName);
    }

    @GetMapping("/byPublicationYear/{year}")
    public ResponseEntity<List<Book>> getAllBooksByPublicationYear(@PathVariable int year) {
        return bookService.getAllBooksByPublicationYear(year);
    }

    @GetMapping("/byPublicationYearWithGenre/{year}/{genre}")
    public ResponseEntity<List<Book>> getAllBooksByPublicationYearWithGenre(
            @PathVariable int year,
            @PathVariable String genre) {
        return bookService.getAllBooksByPublicationYearWithGenre(year, genre);
    }

    @GetMapping("/byPublicationYearRange/{startYear}/{endYear}")
    public ResponseEntity<List<Book>> getAllBooksByPublicationYearRange(
            @PathVariable int startYear,
            @PathVariable int endYear) {
        return bookService.getAllBooksByPublicationYear(startYear, endYear);
    }

    @GetMapping("/byPublicationYearAndGenre/{startYear}/{endYear}/{genre}")
    public ResponseEntity<List<Book>> getAllBooksByPublicationYearAndGenre(
            @PathVariable int startYear,
            @PathVariable int endYear,
            @PathVariable String genre) {
        return bookService.getAllBooksByPublicationYearAndGenre(startYear, endYear, genre);
    }

    @GetMapping("/totalBookCount")
    public ResponseEntity<String> calculateTotalBookCount() {
        return bookService.calculateTotalBookCount();
    }

    @GetMapping("/totalBookInGenre")
    public ResponseEntity<String> calculateTotalBookInGenre(String genre) {
        return bookService.calculateTotalBookInGenre(genre);
    }

    @GetMapping("/totalBookInGenreVice")
    public ResponseEntity<Map<String, Integer>> calculateTotalBookInGenreVice() {
        return bookService.calculateTotalBookInGenreVice();
    }
}
