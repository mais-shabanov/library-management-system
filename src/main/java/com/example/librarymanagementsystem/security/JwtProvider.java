package com.example.librarymanagementsystem.security;

import com.example.librarymanagementsystem.controller.AuthController;
import com.example.librarymanagementsystem.exception.TokeNotValidException;
import com.example.librarymanagementsystem.service.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parserBuilder;
import static io.jsonwebtoken.Jwts.builder;

@Service
public class JwtProvider {



    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @Value("${jwt.signing.key}")
    private String signingKey;

    public String generateToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();
        return builder()
                .setSubject(userPrincipal.getUsername())
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

//    public String generateToken(UserDetails userDetails) {
//        return builder()
//                .setSubject(userDetails.getUsername())
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .setIssuedAt(Date.from(Instant.now()))
//                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
//                .compact();
//    }

    public boolean validateToken(String jwtToken) {
//        Logger logger = LoggerFactory.getLogger(AuthController.class);
//        try {
//            parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwtToken);
//            return true;
//        } catch (SignatureException e) {
//            logger.error("Invalid JWT signature: {}", e.getMessage());
//        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            logger.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            logger.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            logger.error("JWT claims string is empty: {}", e.getMessage());
//        }
        parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwtToken);
        return true;
//        return false;
    }

    public String getUsernameFromJwt(String token) {
        return parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

//    public String generateTokenWithUsername(String username) {
//        return builder()
//                .setSubject(username)
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .setIssuedAt(Date.from(Instant.now()))
//                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
//                .compact();
//    }

//    public long getJwtExpirationInMillis() {
//        return jwtExpirationInMillis;
//    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(keyBytes);
//        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
