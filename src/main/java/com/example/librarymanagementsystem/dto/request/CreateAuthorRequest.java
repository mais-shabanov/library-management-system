package com.example.librarymanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateAuthorRequest(@NotBlank String name) {}

