package com.example.librarymanagementsystem.validator;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {

    // Write a better regex expression
    @Override
    public boolean test(String s) {
        if (s.contains("@"))
            return true;
        return false;
    }
}
