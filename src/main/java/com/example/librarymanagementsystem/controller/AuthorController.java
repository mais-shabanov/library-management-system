package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.request.CreateAuthorRequest;
import com.example.librarymanagementsystem.dto.request.UpdateAuthorRequest;
import com.example.librarymanagementsystem.dto.response.AuthorDto;
import com.example.librarymanagementsystem.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<Set<AuthorDto>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createAuthor(@Valid @RequestBody CreateAuthorRequest createAuthorRequest) {
        authorService.createAuthor(createAuthorRequest);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateAuthor(@Valid @RequestBody UpdateAuthorRequest updateAuthorRequest) {
        authorService.updateAuthor(updateAuthorRequest);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(OK);
    }
}
