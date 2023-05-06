package com.example.librarymanagementsystem.dto.response;

import com.example.librarymanagementsystem.model.Category;

public record CategoryDto (
        Long id,
        String name
) {
    public static CategoryDto convert(Category from) {
        return new CategoryDto(from.getId(), from.getName());
    }
}
