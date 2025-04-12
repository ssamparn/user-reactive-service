package com.service.user.user_reactive_service.service;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface AuthenticationService {
    /* *
     * Mono<Map<UserId, userId-value>, <JwtAccessToken, accessToken-value>>
     * Note: User's email represents userName here
     * */
    Mono<Map<String, String>> authenticate(String userName, String password);
}
