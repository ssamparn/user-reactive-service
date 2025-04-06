package com.service.user.user_reactive_service.mapper;

import com.service.user.user_reactive_service.entity.UserEntity;
import com.service.user.user_reactive_service.web.request.CreateUserRequest;
import com.service.user.user_reactive_service.web.response.CreateUserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toUserEntity(CreateUserRequest request) {
        UserEntity entity = new UserEntity();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setEmail(request.getEmail());
        entity.setPassword(request.getPassword());
        return entity;
    }

    public CreateUserResponse toUserResponse(UserEntity entity) {
        return CreateUserResponse.create(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail()
        );
    }
}
