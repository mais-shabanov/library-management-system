package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.request.CreateCategoryRequest;
import com.example.librarymanagementsystem.dto.request.UpdateCategoryRequest;
import com.example.librarymanagementsystem.dto.response.CategoryDto;
import com.example.librarymanagementsystem.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Set<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        categoryService.createCategory(createCategoryRequest);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateCategory(@Valid @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        categoryService.updateCategory(updateCategoryRequest);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(OK);
    }

}
