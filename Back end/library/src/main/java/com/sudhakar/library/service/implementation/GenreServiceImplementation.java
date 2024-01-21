package com.sudhakar.library.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sudhakar.library.entity.Book;
import com.sudhakar.library.entity.Genre;
import com.sudhakar.library.exception.DuplicateGenreException;
import com.sudhakar.library.repository.BookRepository;
import com.sudhakar.library.repository.GenreRepository;
import com.sudhakar.library.service.GenreService;

@Service
public class GenreServiceImplementation implements GenreService {

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public ResponseEntity<Genre> createGenre(Genre genre) {
        try {
            if (genreRepository.existsByName(genre.getName())) {
                throw new DuplicateGenreException("Genre already exists.");
            }

            Genre savedGenre = genreRepository.save(genre);
            return new ResponseEntity<>(savedGenre, HttpStatus.CREATED);
        } catch (DuplicateGenreException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Genre> updateGenreByName(String name, Genre updatedGenre) {
        try {
            Optional<Genre> optionalGenre = genreRepository.findByName(name);
            System.out.println(optionalGenre.get());
            if (optionalGenre.isPresent()) {

                Genre existingGenre = optionalGenre.get();
                existingGenre
                        .setName(updatedGenre.getName() != null ? updatedGenre.getName() : existingGenre.getName());
                existingGenre.setRackNumber(updatedGenre.getRackNumber() != 0 ? updatedGenre.getRackNumber()
                        : existingGenre.getRackNumber());
                existingGenre
                        .setFloor(updatedGenre.getFloor() != 0 ? updatedGenre.getFloor() : existingGenre.getFloor());
                existingGenre.setDescription(updatedGenre.getDescription() != null ? updatedGenre.getDescription()
                        : existingGenre.getDescription());

                Genre savedGenre = genreRepository.save(existingGenre);

                List<Book> books = bookRepository.findByGenre(existingGenre);
                if (!books.isEmpty()) {
                    for (Book book : books) {
                        book.setGenre(updatedGenre);
                        bookRepository.save(book);
                    }
                }
                return new ResponseEntity<>(savedGenre, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> deleteGenreByName(String name) {
        try {
            Optional<Genre> optionalGenre = genreRepository.findByName(name);
            if (optionalGenre.isPresent()) {

                List<Book> books = bookRepository.findByGenre(optionalGenre.get());
                for (Book book : books) {
                    bookRepository.delete(book);
                }
                genreRepository.delete(optionalGenre.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Genre>> getAllGenres() {
        try {
            List<Genre> genres = genreRepository.findAll();
            return new ResponseEntity<>(genres, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Genre> getGenreByName(String name) {
        try {
            Optional<Genre> optionalGenre = genreRepository.findByName(name);
            if (optionalGenre.isPresent()) {
                Genre genre = optionalGenre.get();
                return new ResponseEntity<>(genre, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Genre>> getGenresByRackNumber(int rackNumber) {
        try {
            List<Genre> genres = genreRepository.findByRackNumber(rackNumber);
            if (!genres.isEmpty()) {
                return new ResponseEntity<>(genres, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Genre>> getGenresByFloor(int floor) {
        try {
            List<Genre> genres = genreRepository.findByFloor(floor);
            if (!genres.isEmpty()) {
                return new ResponseEntity<>(genres, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Genre>> getGenresByRackNumberAndFloor(int rackNumber, int floor) {
        try {
            List<Genre> genres = genreRepository.findByRackNumberAndFloor(rackNumber, floor);
            if (!genres.isEmpty()) {
                return new ResponseEntity<>(genres, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
