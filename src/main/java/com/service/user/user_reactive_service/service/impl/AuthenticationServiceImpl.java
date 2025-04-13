package com.service.user.user_reactive_service.service.impl;

import com.service.user.user_reactive_service.entity.UserEntity;
import com.service.user.user_reactive_service.repository.UserRepository;
import com.service.user.user_reactive_service.service.AuthenticationService;
import com.service.user.user_reactive_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ReactiveAuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    /* *
     * User's email represents userName here
     * */
    @Override
    public Mono<Map<String, String>> authenticate(String userName, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password))
                .then(getUserEntity(userName))
                .map(this::createAuthResponse);
    }

    private Mono<UserEntity> getUserEntity(String userName) {
        return this.userRepository.findByEmail(userName);
    }

    private Map<String, String> createAuthResponse(UserEntity userEntity) {
        Map<String, String> authResponse = new HashMap<>();
        authResponse.put("userId", userEntity.getId().toString());
        authResponse.put("accessToken", jwtService.generateJwtToken(userEntity.getId().toString()));
        return authResponse;
    }
}
