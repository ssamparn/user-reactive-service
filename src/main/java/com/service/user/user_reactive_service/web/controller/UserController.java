package com.service.user.user_reactive_service.web.controller;

import com.service.user.user_reactive_service.service.UserService;
import com.service.user.user_reactive_service.web.model.request.CreateUserRequest;
import com.service.user.user_reactive_service.web.model.response.CreateUserResponse;
import com.service.user.user_reactive_service.web.model.response.GetUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public Mono<ResponseEntity<CreateUserResponse>> getUser(@RequestBody @Valid Mono<CreateUserRequest> createUserRequestMono) {
        return userService.createUser(createUserRequestMono)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                        .location(URI.create("/users/" + response.getId()))
                        .body(response));
    }

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<GetUserResponse>> getUser(@PathVariable("userId") UUID userId) {
        return userService.getUser(userId)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @GetMapping
    public Flux<GetUserResponse> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "limit", defaultValue = "50") int limit) {
        return userService.getAllUsers(page, limit);
    }
}
