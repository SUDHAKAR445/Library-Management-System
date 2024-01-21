package com.sudhakar.library.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sudhakar.library.entity.Book;
import com.sudhakar.library.entity.Publisher;
import com.sudhakar.library.exception.DuplicatePublisherException;
import com.sudhakar.library.repository.BookRepository;
import com.sudhakar.library.repository.PublisherRepository;
import com.sudhakar.library.service.PublisherService;

@Service
public class PublisherServiceImplementation implements PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        try {
            List<Publisher> publishers = publisherRepository.findAll();
            return new ResponseEntity<>(publishers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Publisher> createPublisher(Publisher publisher) {
        try {
            if (publisherRepository.existsByPublisherId(publisher.getPublisherId())) {
                throw new DuplicatePublisherException("Publisher Id already exists.");
            }

            Publisher savedPublisher = publisherRepository.save(publisher);
            return new ResponseEntity<>(savedPublisher, HttpStatus.CREATED);
        } catch (DuplicatePublisherException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Publisher> updatePublisherByPublisherId(String publisherId, Publisher updatedPublisher) {
        try {
            Optional<Publisher> optionalPublisher = publisherRepository.findByPublisherId(publisherId);
            if (optionalPublisher.isPresent()) {
                Publisher existingPublisher = optionalPublisher.get();
                existingPublisher.setPublisherName(
                        updatedPublisher.getPublisherName() != null ? updatedPublisher.getPublisherName()
                                : existingPublisher.getPublisherName());

                Publisher savedPublisher = publisherRepository.save(existingPublisher);

                List<Book> books = bookRepository.findByPublisher(existingPublisher);

                for (Book book : books) {
                    book.setPublisher(existingPublisher);
                    bookRepository.save(book);
                }

                return new ResponseEntity<>(savedPublisher, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> deletePublisherByPublisherId(String publisherId) {
        try {
            Optional<Publisher> optionalPublisher = publisherRepository.findByPublisherId(publisherId);
            if (optionalPublisher.isPresent()) {
                List<Book> books = bookRepository.findByPublisher(optionalPublisher.get());
                for (Book book : books) {
                    bookRepository.delete(book);
                }
                publisherRepository.delete(optionalPublisher.get());

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Publisher> getPublisherById(String publisherId) {
        try {
            Optional<Publisher> optionalPublisher = publisherRepository.findByPublisherId(publisherId);
            if (optionalPublisher.isPresent()) {
                return new ResponseEntity<>(optionalPublisher.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
