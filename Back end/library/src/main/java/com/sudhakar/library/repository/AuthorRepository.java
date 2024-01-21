package com.sudhakar.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sudhakar.library.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByAuthorId(String authorId);

    List<Author> findByAuthorName(String authorName);

    boolean existsByAuthorId(String authorId);
}
