package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String email;
    private String fName;
    private String lName;

    public UserResponse(String email, String fName, String lName) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
    }
}
