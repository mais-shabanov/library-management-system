package com.example.librarymanagementsystem.dto.response;

import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.Publisher;

public record BookDto (
     Long id,
     String isbn,
     DetailDto detail,
     PublisherDto publisher
) {
    public static BookDto convert(Book from) {
        return new BookDto(
                from.getId(),
                from.getIsbn(),
                DetailDto.convert(from.getDetail()),
                PublisherDto.convert(from.getPublisher())
        );
    }
}


