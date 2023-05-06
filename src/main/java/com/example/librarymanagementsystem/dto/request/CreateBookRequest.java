package com.example.librarymanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBookRequest(
        @NotBlank String isbn,
        @NotNull Long detailId,
        @NotNull Long publisherId
) {}
