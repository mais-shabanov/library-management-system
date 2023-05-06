package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "details")
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String title;
    private String description;
    private Integer unitsInStock = 0;
    @OneToMany(mappedBy = "detail")
    private Set<Book> books;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "details_authors",
            joinColumns = {
                    @JoinColumn(name = "detail_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "author_id")
            }
    )
    private Set<Author> authors = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "details_categories",
            joinColumns = {
                    @JoinColumn(name = "detail_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "category_id")
            }
    )
    private Set<Category> categories = new HashSet<>();

    public Detail() {
    }

    public Detail(
            String title,
            String description,
            Integer unitsInStock,
            Set<Book> books,
            Set<Author> authors,
            Set<Category> categories
    ) {
        this.title = title;
        this.description = description;
        this.unitsInStock = unitsInStock;
        this.books = books;
        this.authors = authors;
        this.categories = categories;
    }

    public Detail(
            Long id,
            String title,
            String description,
            Integer unitsInStock,
            Set<Book> books,
            Set<Author> authors,
            Set<Category> categories
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.unitsInStock = unitsInStock;
        this.books = books;
        this.authors = authors;
        this.categories = categories;
    }

    public Detail(
            String title,
            String description,
            Integer unitsInStock,
            Set<Author> authors,
            Set<Category> categories
    ) {
        this.title = title;
        this.description = description;
        this.unitsInStock = unitsInStock;
        this.authors = authors;
        this.categories = categories;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Integer unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
