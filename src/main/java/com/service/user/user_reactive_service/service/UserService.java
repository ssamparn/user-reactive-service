package com.service.user.user_reactive_service.service;

import com.service.user.user_reactive_service.web.request.CreateUserRequest;
import com.service.user.user_reactive_service.web.response.CreateUserResponse;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<CreateUserResponse> createUser(Mono<CreateUserRequest> request);
}