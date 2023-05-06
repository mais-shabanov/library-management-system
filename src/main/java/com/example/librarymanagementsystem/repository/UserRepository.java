package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByFinCode(String finCode);

    @Query(value = "SELECT book.*\n" +
            "FROM users\n" +
            "JOIN transaction ON users.id = transaction.user_id\n" +
            "JOIN book ON transaction.book_id = book.id\n" +
            "WHERE users.fin_code = :finCode ", nativeQuery = true)
    Set<Object[]> findGivenBooksByFinCode(@Param("finCode") String finCode);

    Optional<User> findByName(String username);

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.enabled = TRUE WHERE u.email = ?1"
    )
    void enableUser(String email);

    Boolean existsByFinCode(String finCode);
}
