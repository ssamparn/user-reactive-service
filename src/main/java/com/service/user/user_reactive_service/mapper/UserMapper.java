package com.service.user.user_reactive_service.mapper;

import com.service.user.user_reactive_service.entity.UserEntity;
import com.service.user.user_reactive_service.web.model.request.CreateUserRequest;
import com.service.user.user_reactive_service.web.model.response.CreateUserResponse;
import com.service.user.user_reactive_service.web.model.response.GetUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    /* *
     * Password encryption is potentially a blocking operation for security reasons.
     * It was designed to be complex operation and hence, it is CPU intensive work, and because it is CPU intensive work, it is not quick, and it will block thread until it completes.
     * So in situations like this, to make this operation non-blocking, we can wrap potentially blocking operation like password encoding in a way that it doesn't execute immediately.
     * Instead, it waits until someone actually needs the result. Think of it like setting up a task that will run only when it is hinted, and this will help us keep our application responsive.
     * So by combining Mono.fromCallable() with subscribeOn(), we make sure that this CPU intensive work like password encoding
     * is executed on a separate thread from a pool that is specifically designed for such blocking tasks.
     * And this will make password encoding operation non-blocking and our application more responsive.
     * */
    public Mono<UserEntity> toUserEntityMono(CreateUserRequest request) {
        return Mono.fromCallable(() -> {
            UserEntity entity = new UserEntity();
            entity.setFirstName(request.getFirstName());
            entity.setLastName(request.getLastName());
            entity.setEmail(request.getEmail());
            entity.setPassword(passwordEncoder.encode(request.getPassword()));
            return entity;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public CreateUserResponse toCreateUserResponse(UserEntity entity) {
        return CreateUserResponse.create(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail()
        );
    }

    public GetUserResponse toGetUserResponse(UserEntity entity) {
        return GetUserResponse.create(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail()
        );
    }

    public UserDetails toUserDetails(UserEntity entity) {
        return User.withUsername(entity.getEmail())
                .password(entity.getPassword())
                .authorities(new ArrayList<>())
                .build();
    }
}
