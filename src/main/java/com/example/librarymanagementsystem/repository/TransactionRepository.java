package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE FUNCTION('DATE', t.issueDate) = :date")
    Set<Transaction> findByIssueDate(@Param("date") LocalDate date);

    @Query("SELECT t FROM Transaction t WHERE FUNCTION('DATE', t.returnDate) = :date")
    Set<Transaction> findByReturnDate(@Param("date") LocalDate date);

    @Query(value = "SELECT a.*, COUNT(*) AS num_given\n" +
            "FROM book\n" +
            "JOIN details_authors ON book.detail_id = details_authors.detail_id\n" +
            "JOIN author a ON details_authors.author_id = a.id\n" +
            "JOIN transaction ON book.id = transaction.book_id\n" +
            "GROUP BY a.id\n" +
            "ORDER BY num_given DESC\n" +
            "LIMIT 1;", nativeQuery = true)
    Set<Object[]> findMostSellingAuthors();
}
