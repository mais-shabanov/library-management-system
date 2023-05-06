package com.example.librarymanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreatePublisherRequest(@NotBlank String name) {}
