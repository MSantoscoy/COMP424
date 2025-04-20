package com.example.demo.dto;
import jakarta.validation.constraints.*;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    //annotations that handle blank and invalid email entries
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    private String password;
}
