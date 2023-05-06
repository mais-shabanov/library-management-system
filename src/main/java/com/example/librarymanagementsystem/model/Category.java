package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private Set<Detail> details = new HashSet<>();

    public Category() {

    }

    public Category(String name, Set<Detail> details) {
        this.name = name;
        this.details = details;
    }

    public Category(Long id, String name, Set<Detail> details) {
        this.id = id;
        this.name = name;
        this.details = details;
    }

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Detail> getDetails() {
        return details;
    }

    public void setDetails(Set<Detail> details) {
        this.details = details;
    }
}
