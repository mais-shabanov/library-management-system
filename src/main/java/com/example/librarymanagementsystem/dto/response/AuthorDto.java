package com.example.librarymanagementsystem.dto.response;

import com.example.librarymanagementsystem.model.Author;

public record AuthorDto (
        Long id,
        String name
) {
    public static AuthorDto convert(Author from) {
        return new AuthorDto(from.getId(), from.getName());
    }
}


