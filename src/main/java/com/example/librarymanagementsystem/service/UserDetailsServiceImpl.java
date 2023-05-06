package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Role;
import com.example.librarymanagementsystem.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userService.findByEmail(email);

        return new org.springframework.security.core.userdetails.User(
                user.getFinCode(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
//                getAuthorities("USER")
                getAuthorities(user)
        );
    }

//    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
//        return singletonList(new SimpleGrantedAuthority(role));
//    }
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName().name()))
                .collect(Collectors.toList());
    }


}
