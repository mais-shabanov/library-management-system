package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String isbn;
    @ManyToOne
    @JoinColumn
    private Detail detail;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
    @OneToMany(mappedBy = "book")
    private Set<Transaction> transactions = new HashSet<>();

    public Book() {

    }

    public Book(
            String isbn,
            Detail detail,
            Publisher publisher
    ) {
        this.isbn = isbn;
        this.detail = detail;
        this.publisher = publisher;
    }

    public Book(
            Long id,
            String isbn,
            Detail detail,
            Publisher publisher
    ) {
        this.id = id;
        this.isbn = isbn;
        this.detail = detail;
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
