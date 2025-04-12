package com.service.user.user_reactive_service.web.controller;

import com.service.user.user_reactive_service.service.AuthenticationService;
import com.service.user.user_reactive_service.web.model.request.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public Mono<ResponseEntity<Object>> login(@RequestBody Mono<AuthenticationRequest> authenticationRequestMono) {
        return authenticationRequestMono
                .flatMap(request -> authenticationService.authenticate(request.getEmail(), request.getPassword()))
                .map(authenticationResponseMap -> ResponseEntity.ok()
                        .headers(headers -> {
                            headers.add(HttpHeaders.AUTHORIZATION, "Bearer ".concat(authenticationResponseMap.get("accessToken")));
                            headers.add("UserId", authenticationResponseMap.get("userId"));
                        })
                .build());
    }
}
