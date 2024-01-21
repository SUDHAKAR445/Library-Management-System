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

import com.sudhakar.library.entity.Publisher;
import com.sudhakar.library.service.PublisherService;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/all")
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("/get/{publisherId}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable String publisherId) {
        return publisherService.getPublisherById(publisherId);
    }

    @PostMapping("/create")
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        return publisherService.createPublisher(publisher);
    }

    @PutMapping("/update/{publisherId}")
    public ResponseEntity<Publisher> updatePublisherById(@PathVariable String publisherId,
            @RequestBody Publisher updatedPublisher) {
        return publisherService.updatePublisherByPublisherId(publisherId, updatedPublisher);
    }

    @DeleteMapping("/delete/{publisherId}")
    public ResponseEntity<Void> deletePublisherById(@PathVariable String publisherId) {
        return publisherService.deletePublisherByPublisherId(publisherId);
    }
}
