package com.example.librarymanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(@NotBlank String name) {}
