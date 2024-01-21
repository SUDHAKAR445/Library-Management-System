package com.sudhakar.library.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sudhakar.library.entity.Publisher;

public interface PublisherService {

    ResponseEntity<List<Publisher>> getAllPublishers();

    ResponseEntity<Publisher> createPublisher(Publisher publisher);

    ResponseEntity<Publisher> updatePublisherByPublisherId(String publisherId, Publisher updatedPublisher);

    ResponseEntity<Void> deletePublisherByPublisherId(String publisherId);

    ResponseEntity<Publisher> getPublisherById(String publisherId);
}
