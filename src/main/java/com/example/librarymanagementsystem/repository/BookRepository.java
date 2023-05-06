package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByIsbn(String isbn);


    @Query(value = "SELECT b.*, COUNT(*) AS total_given\n" +
            "FROM transactions t\n" +
            "JOIN books b ON t.book_id = b.id\n" +
            "GROUP BY b.id\n" +
            "ORDER BY total_given DESC\n" +
            "LIMIT 2;", nativeQuery = true)
    Set<Object[]> findFamousBooks();

}
