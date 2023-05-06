package com.example.librarymanagementsystem.dto.response;

import com.example.librarymanagementsystem.model.Detail;

import java.util.List;
import static java.util.stream.Collectors.toList;

public record DetailDto (
        Long id,
        String title,
        String description,
        Integer unitInStocks,
        List<CategoryDto> categories,
        List<AuthorDto> authors
) {
    public static DetailDto convert(Detail from) {
        return new DetailDto(
                from.getId(),
                from.getTitle(),
                from.getDescription(),
                from.getUnitsInStock(),
                from.getCategories().stream().map(CategoryDto::convert).collect(toList()),
                from.getAuthors().stream().map(AuthorDto::convert).collect(toList())
        );
    }
}
