package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
    private Boolean isReturned;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;

    public Transaction() {
    }

    public Transaction(
            User user,
            Book book,
            Boolean isReturned,
            LocalDateTime issueDate,
            LocalDateTime dueDate,
            LocalDateTime returnDate
    ) {
        this.user = user;
        this.book = book;
        this.isReturned = isReturned;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public Transaction(
            Long id,
            User user,
            Book book,
            Boolean isReturned,
            LocalDateTime issueDate,
            LocalDateTime dueDate,
            LocalDateTime returnDate
    ) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.isReturned = isReturned;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public Transaction(
            User user,
            Book book,
            Boolean isReturned,
            LocalDateTime issueDate,
            LocalDateTime dueDate
    ) {
        this.user = user;
        this.book = book;
        this.isReturned = isReturned;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Boolean getIsReturned() {
        return isReturned;
    }

    public void setIsReturned(Boolean isReturned) {
        this.isReturned = isReturned;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

}
