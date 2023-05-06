package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.exception.ConfirmationTokenNotFoundException;
import com.example.librarymanagementsystem.model.ConfirmationToken;
import com.example.librarymanagementsystem.repository.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;


    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.updateConfirmedAt(
                token,
                LocalDateTime.now()
        );
    }

    public void saveToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getToken(String token) {
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ConfirmationTokenNotFoundException("Confirmation token not found by token : " + token));
    }
}
