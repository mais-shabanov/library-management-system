package com.example.librarymanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTransactionRequest(
        @NotBlank String isbn,
        @NotBlank String finCode,
        @NotNull Long days
) {}