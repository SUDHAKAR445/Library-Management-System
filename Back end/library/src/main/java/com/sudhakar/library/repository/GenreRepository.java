package com.sudhakar.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sudhakar.library.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    boolean existsByName(String name);

    Optional<Genre> findByName(String name);

    List<Genre> findByRackNumber(int rackNumber);

    List<Genre> findByFloor(int floor);

    List<Genre> findByRackNumberAndFloor(int rackNumber, int floor);

}
