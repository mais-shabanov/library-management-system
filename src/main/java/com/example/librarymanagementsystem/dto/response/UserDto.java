package com.example.librarymanagementsystem.dto.response;

import com.example.librarymanagementsystem.model.User;

public record UserDto (
        String name,
        String surname,
        String finCode,
        String password,
        String email,
        String phoneNumber
) {
    public static UserDto convert(User from) {
        return new UserDto(
                from.getName(),
                from.getSurname(),
                from.getFinCode(),
                from.getPassword(),
                from.getEmail(),
                from.getPhoneNumber()
        );
    }
}
