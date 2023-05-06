package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.request.CreatePublisherRequest;
import com.example.librarymanagementsystem.dto.request.UpdatePublisherRequest;
import com.example.librarymanagementsystem.dto.response.PublisherDto;
import com.example.librarymanagementsystem.service.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<Set<PublisherDto>> getAllPublishers() {
        return ResponseEntity.ok(publisherService.getAllPublishers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDto> getPublisherById(@PathVariable Long id) {
        return ResponseEntity.ok(publisherService.getPublisherById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createPublisher(@RequestBody CreatePublisherRequest createPublisherRequest) {
        publisherService.createPublisher(createPublisherRequest);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updatePublisher(@RequestBody UpdatePublisherRequest updatePublisherRequest) {
        publisherService.updatePublisher(updatePublisherRequest);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return new ResponseEntity<>(OK);
    }
}
