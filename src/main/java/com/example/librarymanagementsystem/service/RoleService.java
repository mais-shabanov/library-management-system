package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Role;
import com.example.librarymanagementsystem.model.RoleType;
import com.example.librarymanagementsystem.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(RoleType name) {
        return roleRepository.findByName(name);
//                .orElseThrow(() -> new RoleNotFoundException("Role can't be found by name : " + name));
    }

    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
