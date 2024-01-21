package com.sudhakar.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sudhakar.library.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByPublisherId(String publisherId);

    List<Publisher> findByPublisherName(String publisherName);

    boolean existsByPublisherId(String publisherId);

}
