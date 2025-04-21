package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;


public class UserRequest {

    private String fName;
    private String lName;
    private String email;

    public UserRequest(String fName, String lName, String email) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFName() {
        return fName;
    }

    public String getLName() {
        return lName;
    }
}
