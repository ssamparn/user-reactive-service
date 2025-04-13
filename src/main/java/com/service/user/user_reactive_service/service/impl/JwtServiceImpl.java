package com.service.user.user_reactive_service.service.impl;

import com.service.user.user_reactive_service.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.token.secret}")
    private String jwtTokenSecret;

    @Override
    public String generateJwtToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Optional.ofNullable(jwtTokenSecret)
                .map(String::getBytes)
                .map(Keys::hmacShaKeyFor)
                .orElseThrow(() -> new IllegalArgumentException("jwt.token.secret or jwt.token.secret is null"));
    }

    @Override
    public Mono<Boolean> validateJwtToken(String jwtToken) {
        return Mono.just(jwtToken)
                .map(this::parseToken)
                .map(claims -> !claims.getExpiration().before(new Date()))
                .onErrorReturn(false);
    }

    @Override
    public String extractTokenSubject(String jwtToken) {
        return parseToken(jwtToken).getSubject();
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
}
