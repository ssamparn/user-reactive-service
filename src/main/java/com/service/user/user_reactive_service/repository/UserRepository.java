package com.service.user.user_reactive_service.repository;

import com.service.user.user_reactive_service.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends R2dbcRepository<UserEntity, UUID> {
    Flux<UserEntity> findAllBy(Pageable pageable);

    Mono<UserEntity> findByEmail(String email);
}