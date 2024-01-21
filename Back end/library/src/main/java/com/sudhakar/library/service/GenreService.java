package com.sudhakar.library.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sudhakar.library.entity.Genre;

public interface GenreService {

    ResponseEntity<Genre> createGenre(Genre genre);

    ResponseEntity<Genre> updateGenreByName(String name, Genre genre);

    ResponseEntity<Void> deleteGenreByName(String name);

    ResponseEntity<List<Genre>> getAllGenres();

    ResponseEntity<Genre> getGenreByName(String name);

    ResponseEntity<List<Genre>> getGenresByRackNumber(int rackNumber);

    ResponseEntity<List<Genre>> getGenresByFloor(int floor);

    ResponseEntity<List<Genre>> getGenresByRackNumberAndFloor(int rack_number, int floor);
}
