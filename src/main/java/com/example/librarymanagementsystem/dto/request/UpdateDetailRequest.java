package com.example.librarymanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateDetailRequest(
        @NotNull Long id,
        @NotBlank String title,
        @NotBlank String description,
        @NotNull Integer unitsInStock,
        @NotNull Long categoryId,
        @NotNull Long authorId
) {}