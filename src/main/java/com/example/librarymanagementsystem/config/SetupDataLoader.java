package com.example.librarymanagementsystem.config;

import com.example.librarymanagementsystem.model.Role;
import com.example.librarymanagementsystem.model.RoleType;
import com.example.librarymanagementsystem.model.User;
import com.example.librarymanagementsystem.service.RoleService;
import com.example.librarymanagementsystem.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public SetupDataLoader(
            UserService userService,
            RoleService roleService,
            PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role librarian = createRoleIfNotFound(RoleType.LIBRARIAN);
        User user = new User(
                "Gurban",
                "Alizada",
                "2002",
                true,
                passwordEncoder.encode("gurban2002"),
                "gurban@gmail.com",
                "2002"
        );
        user.setRoles(Set.of(librarian));
        userService.saveUser(user);
    }

    @Transactional
    Role createRoleIfNotFound(RoleType name) {
        Role role = roleService.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleService.saveRole(role);
        }
        return role;
    }
}
