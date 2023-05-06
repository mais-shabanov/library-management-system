package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long> {

    Set<Detail> findByCategories_NameIn(Set<String> categoryNames);

    Optional<Detail> findByTitle(String name);
}
