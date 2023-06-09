package com.example.librarymanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCategoryRequest(@NotNull Long id, @NotBlank String name) {}
