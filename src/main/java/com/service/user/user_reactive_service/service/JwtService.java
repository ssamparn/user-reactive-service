package com.service.user.user_reactive_service.service;

import reactor.core.publisher.Mono;

public interface JwtService {
    String generateJwtToken(String subject);
    Mono<Boolean> validateJwtToken(String jwtToken);
    String extractTokenSubject(String jwtToken);
}
