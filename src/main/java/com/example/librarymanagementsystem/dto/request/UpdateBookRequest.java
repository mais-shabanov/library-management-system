package com.example.librarymanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateBookRequest(
        @NotNull Long id,
        @NotBlank String isbn,
        @NotNull Long detailId,
        @NotNull Long publisherId
) {
}
