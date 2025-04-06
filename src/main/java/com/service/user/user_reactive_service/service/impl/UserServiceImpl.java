package com.service.user.user_reactive_service.service.impl;

import com.service.user.user_reactive_service.mapper.UserMapper;
import com.service.user.user_reactive_service.repository.UserRepository;
import com.service.user.user_reactive_service.service.UserService;
import com.service.user.user_reactive_service.web.request.CreateUserRequest;
import com.service.user.user_reactive_service.web.response.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public Mono<CreateUserResponse> createUser(Mono<CreateUserRequest> requestMono) {
        return requestMono
                .mapNotNull(userMapper::toUserEntity)
                .flatMap(userRepository::save)
                .mapNotNull(userMapper::toUserResponse);
    }
}