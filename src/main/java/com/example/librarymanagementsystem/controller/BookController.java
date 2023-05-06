package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.request.CreateBookRequest;
import com.example.librarymanagementsystem.dto.request.UpdateBookRequest;
import com.example.librarymanagementsystem.dto.response.BookDto;
import com.example.librarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Set<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/category")
    public ResponseEntity<Set<BookDto>> getAllBooksByCategoryNames(@RequestParam Set<String> categoryNames) {
        return ResponseEntity.ok(bookService.getAllBooksByCategoryNames(categoryNames));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        bookService.createBook(createBookRequest);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateBook(@Valid @RequestBody UpdateBookRequest updateBookRequest) {
        bookService.updateBook(updateBookRequest);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/famous-books")
    public ResponseEntity<Set<BookDto>> getFamousBooks() {
        return ResponseEntity.ok(bookService.getFamousBooks());
    }

    @PostMapping("/random")
    public String getRandom() {
        return "You are a true legend!";
    }
}
