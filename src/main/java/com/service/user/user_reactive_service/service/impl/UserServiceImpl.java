package com.service.user.user_reactive_service.service.impl;

import com.service.user.user_reactive_service.mapper.UserMapper;
import com.service.user.user_reactive_service.repository.UserRepository;
import com.service.user.user_reactive_service.service.UserService;
import com.service.user.user_reactive_service.web.model.request.CreateUserRequest;
import com.service.user.user_reactive_service.web.model.response.CreateUserResponse;
import com.service.user.user_reactive_service.web.model.response.GetUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toUserDetails);
    }

    @Override
    public Mono<CreateUserResponse> createUser(Mono<CreateUserRequest> requestMono) {
        return requestMono
                .flatMap(userMapper::toUserEntityMono)
                .flatMap(userRepository::save)
                .mapNotNull(userMapper::toCreateUserResponse);
    }

    @Override
    public Mono<GetUserResponse> getUser(UUID userId) {
        return userRepository.findById(userId)
                .mapNotNull(userMapper::toGetUserResponse);
    }

    @Override
    public Flux<GetUserResponse> getAllUsers(int page, int limit) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, limit);
        return userRepository.findAllBy(pageable)
                .mapNotNull(userMapper::toGetUserResponse);
    }
}