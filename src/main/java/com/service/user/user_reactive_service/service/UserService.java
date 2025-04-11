package com.service.user.user_reactive_service.service;

import com.service.user.user_reactive_service.web.request.CreateUserRequest;
import com.service.user.user_reactive_service.web.response.CreateUserResponse;
import com.service.user.user_reactive_service.web.response.GetUserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {
    Mono<CreateUserResponse> createUser(Mono<CreateUserRequest> request);
    Mono<GetUserResponse> getUser(UUID userId);
    Flux<GetUserResponse> getAllUsers(int page, int limit);
}