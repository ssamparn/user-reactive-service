package com.service.user.user_reactive_service.web.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 50, message = "First name cannot be shorter than 2 and longer than 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, max = 50, message = "Last name cannot be shorter than 2 and longer than 50 characters")
    private String lastName;

    @NotBlank(message = "User email is mandatory")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "User password is mandatory")
    @Size(min = 8, max = 16, message = "User password can not be shorter than 8 or longer than 16 characters")
    private String password;
}
