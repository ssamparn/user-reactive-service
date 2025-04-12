package com.service.user.user_reactive_service.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor(staticName = "create")
public class CreateUserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -2338626292552177485L;

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
