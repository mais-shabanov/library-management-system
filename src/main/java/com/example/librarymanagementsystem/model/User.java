package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String finCode;
    private String password;
    private Boolean enabled = false;
    private String email;
    private String phoneNumber;
    @OneToMany(mappedBy = "user")
    private Set<Transaction> transactions = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(
            String name,
            String surname,
            String finCode,
            String password,
            String email,
            String phoneNumber
    ) {
        this.name = name;
        this.surname = surname;
        this.finCode = finCode;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User(
            String name,
            String surname,
            String finCode,
            Boolean enabled,
            String password,
            String email,
            String phoneNumber
    ) {
        this.name = name;
        this.surname = surname;
        this.finCode = finCode;
        this.enabled = enabled;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFinCode() {
        return finCode;
    }

    public void setFinCode(String finCode) {
        this.finCode = finCode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
