package com.sudhakar.library.service.implementation;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sudhakar.library.entity.Author;
import com.sudhakar.library.entity.Book;
import com.sudhakar.library.entity.Genre;
import com.sudhakar.library.entity.Publisher;
import com.sudhakar.library.exception.DuplicateBookException;
import com.sudhakar.library.repository.AuthorRepository;
import com.sudhakar.library.repository.BookRepository;
import com.sudhakar.library.repository.GenreRepository;
import com.sudhakar.library.repository.PublisherRepository;
import com.sudhakar.library.service.BookService;

@Service
public class BookServiceImplementation implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    GenreRepository genreRepository;

    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = bookRepository.findAll();
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Book> getBookByBookIdOrIsbn(String bookIdOrIsbn) {
        try {
            Optional<Book> optionalBook = bookRepository.findByBookIdOrIsbn(bookIdOrIsbn, bookIdOrIsbn);
            return optionalBook.map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Book> saveBook(Book book) {
        try {
            if (bookRepository.existsByBookIdOrIsbn(book.getBookId(), book.getIsbn())) {
                throw new DuplicateBookException("BookId or ISBN already exists.");
            }

            Optional<Author> optionalAuthor = authorRepository.findByAuthorId(book.getAuthor().getAuthorId());
            Optional<Publisher> optionalPublisher = publisherRepository
                    .findByPublisherId(book.getPublisher().getPublisherId());
            Optional<Genre> optionalGenre = genreRepository.findByName(book.getGenre().getName());

            book.setCreatedAt(new Date());
            Book savedBook;
            if (optionalAuthor.isPresent() && optionalPublisher.isPresent() && optionalGenre.isPresent()) {
                book.setAuthor(optionalAuthor.get());
                book.setPublisher(optionalPublisher.get());
                book.setGenre(optionalGenre.get());
            } else if (optionalAuthor.isPresent() && optionalPublisher.isPresent() && !optionalGenre.isPresent()) {
                book.setAuthor(optionalAuthor.get());
                book.setPublisher(optionalPublisher.get());
            } else if (optionalAuthor.isPresent() && !optionalPublisher.isPresent() && optionalGenre.isPresent()) {
                book.setAuthor(optionalAuthor.get());
                book.setGenre(optionalGenre.get());
            } else if (!optionalAuthor.isPresent() && optionalPublisher.isPresent() && optionalGenre.isPresent()) {
                book.setPublisher(optionalPublisher.get());
                book.setGenre(optionalGenre.get());
            } else if (!optionalAuthor.isPresent() && !optionalPublisher.isPresent() && optionalGenre.isPresent()) {
                book.setGenre(optionalGenre.get());
            } else if (!optionalAuthor.isPresent() && optionalPublisher.isPresent() && !optionalGenre.isPresent()) {
                book.setPublisher(optionalPublisher.get());
            } else if (optionalAuthor.isPresent() && !optionalPublisher.isPresent() && !optionalGenre.isPresent()) {
                book.setAuthor(optionalAuthor.get());
            }
            savedBook = bookRepository.save(book);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (DuplicateBookException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Book> updateBookByBookIdOrIsbn(String bookIdOrIsbn, Book updatedBook) {
        try {
            if (updatedBook == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Optional<Book> optionalBook = bookRepository.findByBookIdOrIsbn(bookIdOrIsbn, bookIdOrIsbn);

            if (optionalBook.isPresent()) {
                Book existingBook = optionalBook.get();
                existingBook.setBookId(
                        updatedBook.getBookId() != null ? updatedBook.getBookId() : existingBook.getBookId());
                existingBook
                        .setTitle(updatedBook.getTitle() != null ? updatedBook.getTitle() : existingBook.getTitle());
                existingBook.setAuthor(
                        updatedBook.getAuthor() != null ? updatedBook.getAuthor() : existingBook.getAuthor());
                existingBook
                        .setGenre(updatedBook.getGenre() != null ? updatedBook.getGenre() : existingBook.getGenre());
                existingBook.setIsbn(updatedBook.getIsbn() != null ? updatedBook.getIsbn() : existingBook.getIsbn());
                existingBook.setPublicationYear(
                        updatedBook.getPublicationYear() != 0 ? updatedBook.getPublicationYear()
                                : existingBook.getPublicationYear());
                existingBook.setCreatedAt(
                        updatedBook.getCreatedAt() != null ? updatedBook.getCreatedAt() : existingBook.getCreatedAt());
                existingBook.setPublisher(
                        updatedBook.getPublisher() != null ? updatedBook.getPublisher() : existingBook.getPublisher());
                existingBook.setAvailableQuantity(
                        updatedBook.getAvailableQuantity() != 0 ? updatedBook.getAvailableQuantity()
                                : existingBook.getAvailableQuantity());

                Book savedBook = bookRepository.save(existingBook);
                return new ResponseEntity<>(savedBook, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> deleteBookByBookIdOrIsbn(String bookIdOrIsbn) {
        try {
            Optional<Book> optionalBook = bookRepository.findByBookIdOrIsbn(bookIdOrIsbn, bookIdOrIsbn);

            if (optionalBook.isPresent()) {
                bookRepository.delete(optionalBook.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Book>> getBooksByGenre(String genre) {
        try {
            List<Book> books = bookRepository.findByGenreName(genre);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Book>> getBooksByTitle(String title) {
        try {
            List<Book> books = bookRepository.findByTitle(title);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Book>> getAllBooksByPublisherIdOrPublisherName(String publisherIdOrPublisherName) {
        try {
            List<Book> books = bookRepository.findByPublisherPublisherIdOrPublisherPublisherName(
                    publisherIdOrPublisherName, publisherIdOrPublisherName);

            if (!books.isEmpty()) {
                return new ResponseEntity<>(books, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Book>> getAllBooksByAuthorIdOrAuthorName(String authorIdOrAuthorName) {
        try {
            List<Book> books = bookRepository.findByAuthorAuthorIdOrAuthorAuthorName(
                    authorIdOrAuthorName, authorIdOrAuthorName);

            if (!books.isEmpty()) {
                return new ResponseEntity<>(books, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Book>> getAllBooksByPublicationYear(int year) {
        try {
            if (year == 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<Book> books = bookRepository.findByPublicationYear(year);
            if (!books.isEmpty()) {
                return new ResponseEntity<>(books, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Book>> getAllBooksByPublicationYearWithGenre(int year, String genre) {
        try {
            List<Book> books = bookRepository.findBooksByPublicationYearWithGenre(year, genre);
            if (!books.isEmpty()) {
                return new ResponseEntity<>(books, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Book>> getAllBooksByPublicationYearAndGenre(int startYear, int endYear, String genre) {
        try {
            if (startYear == 0 || endYear == 0 || genre == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<Book> books = bookRepository.findBooksByPublicationYearAndGenre(startYear, endYear, genre);
            if (!books.isEmpty()) {
                return new ResponseEntity<>(books, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Book>> getAllBooksByPublicationYear(int startYear, int endYear) {
        try {
            if (startYear == 0 || endYear == 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<Book> books = bookRepository.findBooksByPublicationYear(startYear, endYear);
            if (!books.isEmpty()) {
                return new ResponseEntity<>(books, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> calculateTotalBookCount() {
        try {
            List<Book> books = bookRepository.findAll();
            int total = 0;
            if (!books.isEmpty()) {
                for (int i = 0; i < books.size(); i++) {
                    total += books.get(i).getAvailableQuantity();
                }
                return new ResponseEntity<>("Total books in Library : " + total, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Sorry, No books in Library", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> calculateTotalBookInGenre(String genre) {
        try {
            List<Book> books = bookRepository.findByGenreName(genre);
            int total = 0;
            if (!books.isEmpty()) {
                for (int i = 0; i < books.size(); i++) {
                    total += books.get(i).getAvailableQuantity();
                }
                return new ResponseEntity<>("Total books in " + genre + " : " + total, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Sorry, No books in " + genre, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Integer>> calculateTotalBookInGenreVice() {
        try {
            List<Book> books = bookRepository.findAll();
            Map<String, Integer> genreCount = new LinkedHashMap<>();
            if (!books.isEmpty()) {
                for (int i = 0; i < books.size(); i++) {
                    Book book = books.get(i);
                    if (genreCount.containsKey(book.getGenre().getName())) {
                        genreCount.put(book.getGenre().getName(),
                                genreCount.get(book.getGenre().getName()) + book.getAvailableQuantity());
                    } else {
                        genreCount.put(book.getGenre().getName(), book.getAvailableQuantity());
                    }
                }
                return new ResponseEntity<>(genreCount, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
