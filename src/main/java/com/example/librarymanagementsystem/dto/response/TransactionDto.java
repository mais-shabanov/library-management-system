package com.example.librarymanagementsystem.dto.response;

import com.example.librarymanagementsystem.model.Transaction;

import java.time.LocalDateTime;

public record TransactionDto (
        Long id,
        UserDto user,
        BookDto book,
        Boolean isReturned,
        LocalDateTime issueDate,
        LocalDateTime dueDate,
        LocalDateTime returnDate
) {
    public static TransactionDto convert(Transaction from) {
        return new TransactionDto(
                from.getId(),
                UserDto.convert(from.getUser()),
                BookDto.convert(from.getBook()),
                from.getIsReturned(),
                from.getIssueDate(),
                from.getDueDate(),
                from.getReturnDate()
        );
    }
}

