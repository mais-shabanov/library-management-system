package com.example.librarymanagementsystem.dto.request;

import com.example.librarymanagementsystem.model.RoleType;
import com.example.librarymanagementsystem.model.User;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

public record RegisterRequest(
        String name,
        String surname,
        String finCode,
        String password,
        String email,
        String phoneNumber,
        Set<RoleType> roles
) {
}

