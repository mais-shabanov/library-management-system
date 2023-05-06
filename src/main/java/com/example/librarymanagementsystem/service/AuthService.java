package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.request.LoginRequest;
import com.example.librarymanagementsystem.dto.request.RegisterRequest;
import com.example.librarymanagementsystem.dto.response.AuthenticationResponse;
import com.example.librarymanagementsystem.exception.ConfirmationTokenExpiredException;
import com.example.librarymanagementsystem.exception.EmailAlreadyConfirmedException;
import com.example.librarymanagementsystem.exception.UserAlreadyExistsException;
import com.example.librarymanagementsystem.model.*;
import com.example.librarymanagementsystem.security.JwtProvider;
import com.example.librarymanagementsystem.validator.EmailValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final RoleService roleService;
    private final EmailService emailService;
    private final EmailValidator emailValidator;
    private ConfirmationTokenService confirmationTokenService;

    public AuthService(
            UserService userService,
            JwtProvider jwtProvider,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService,
            RoleService roleService,
            EmailService emailService,
            EmailValidator emailValidator,
            ConfirmationTokenService confirmationTokenService
    ) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.roleService = roleService;
        this.emailService = emailService;
        this.emailValidator = emailValidator;
        this.confirmationTokenService = confirmationTokenService;
    }

    public void register(RegisterRequest request) {

        Boolean isEmailValid = emailValidator.test(request.email());

        if (!isEmailValid) {
            throw new IllegalStateException("Email not valid!");
        }

        if (userService.existsByFinCode(request.finCode())) {
            throw new UserAlreadyExistsException("User already exists by fin code : " + request.finCode());
        }

        var user = new User(
                request.name(),
                request.surname(),
                request.finCode(),
                passwordEncoder.encode(request.password()),
                request.email(),
                request.phoneNumber()
        );
        Set<Role> roles = new HashSet<>();
        for (var roleName : request.roles()) {
            var role = new Role(roleName);
            roleService.saveRole(role);
            roles.add(role);
        }
        user.setRoles(roles);
        userService.saveUser(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1),
                user
        );

        confirmationTokenService.saveToken(confirmationToken);

        String link = "http://localhost:8080/api/v1/auth/confirm?token=" + confirmationToken.getToken();
        emailService.send(
                request.email(),
                String.format("Hello %s, Please click the link to confirm your account : %s", request.name(), link)
        );
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(token);
    }

    @Transactional
    public String confirm(String token) {
        ConfirmationToken confirmationToken =
                confirmationTokenService.getToken(token);
        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyConfirmedException("Email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenExpiredException("Confirmation token expired");
        }
        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(confirmationToken.getUser().getEmail());
        return "Your account confirmed successfully";
    }
}
