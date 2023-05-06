package com.example.librarymanagementsystem.dto.response;

import com.example.librarymanagementsystem.model.Publisher;

public record PublisherDto (
    Long id,
    String name
) {
    public static PublisherDto convert(Publisher from) {
        return new PublisherDto(from.getId(), from.getName());
    }
}