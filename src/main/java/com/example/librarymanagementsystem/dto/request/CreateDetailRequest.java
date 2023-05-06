package com.example.librarymanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateDetailRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull Integer unitsInStock,
        @NotNull Set<Long> categoryIds,
        @NotNull Set<Long> authorIds
) {}
