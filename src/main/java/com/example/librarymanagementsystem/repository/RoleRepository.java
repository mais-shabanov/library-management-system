package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Role;
import com.example.librarymanagementsystem.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleType name);
}
