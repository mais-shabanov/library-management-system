package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.response.AuthorDto;
import com.example.librarymanagementsystem.dto.response.BookDto;
import com.example.librarymanagementsystem.dto.response.UserDto;
import com.example.librarymanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/given-books/{finCode}")
    public ResponseEntity<Set<BookDto>> getGivenBooksByFinCode(@PathVariable String finCode){
        return ResponseEntity.ok(userService.getGivenBooksByFinCode(finCode));
    }

    @GetMapping
    public ResponseEntity<Set<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
