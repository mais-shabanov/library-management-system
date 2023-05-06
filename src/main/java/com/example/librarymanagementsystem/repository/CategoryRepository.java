package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByName(String name);
}
