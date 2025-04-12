package com.service.user.user_reactive_service.service;

import com.service.user.user_reactive_service.web.model.request.CreateUserRequest;
import com.service.user.user_reactive_service.web.model.response.CreateUserResponse;
import com.service.user.user_reactive_service.web.model.response.GetUserResponse;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService extends ReactiveUserDetailsService {
    Mono<CreateUserResponse> createUser(Mono<CreateUserRequest> request);
    Mono<GetUserResponse> getUser(UUID userId);
    Flux<GetUserResponse> getAllUsers(int page, int limit);
}