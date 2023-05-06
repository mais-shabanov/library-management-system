package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Token;
import com.example.librarymanagementsystem.model.TokenType;
import com.example.librarymanagementsystem.model.User;
import com.example.librarymanagementsystem.repository.TokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public void saveToken(User user, String token) {
        var savedToken = new Token(
                token,
                TokenType.BEARER,
                false,
                false,
                user
        );
        tokenRepository.save(savedToken);
    }

    @Transactional
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
