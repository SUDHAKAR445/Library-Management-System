package com.sudhakar.library.controller;

import java.util.List;

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

import com.sudhakar.library.entity.Genre;
import com.sudhakar.library.service.GenreService;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping("/create")
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
        return genreService.createGenre(genre);
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<Genre> updateGenreByName(@PathVariable String name, @RequestBody Genre updatedGenre) {
        return genreService.updateGenreByName(name, updatedGenre);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> deleteGenreByName(@PathVariable String name) {
        return genreService.deleteGenreByName(name);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Genre>> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<Genre> getGenreByName(@PathVariable String name) {
        return genreService.getGenreByName(name);
    }

    @GetMapping("/by-rack/{rackNumber}")
    public ResponseEntity<List<Genre>> getGenresByRackNumber(@PathVariable int rackNumber) {
        return genreService.getGenresByRackNumber(rackNumber);
    }

    @GetMapping("/by-floor/{floor}")
    public ResponseEntity<List<Genre>> getGenresByFloor(@PathVariable int floor) {
        return genreService.getGenresByFloor(floor);
    }

    @GetMapping("/by-rack-floor/{rackNumber}/{floor}")
    public ResponseEntity<List<Genre>> getGenresByRackNumberAndFloor(
            @PathVariable int rackNumber,
            @PathVariable int floor) {
        return genreService.getGenresByRackNumberAndFloor(rackNumber, floor);
    }
}
