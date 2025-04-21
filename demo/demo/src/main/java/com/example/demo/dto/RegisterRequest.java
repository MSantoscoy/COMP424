package com.example.demo.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegisterRequest {
    private String fName;
    private String lName;
    private String email;
    private String userPass;
    private LocalDate dob;
    private List<SecurityAnswer> securityAnswers = new ArrayList<>(); // default initialization

    public RegisterRequest() {
        // no-arg constructor
    }
    public RegisterRequest(String fName, String lName, String email, String userPass, LocalDate dob, List<SecurityAnswer> securityAnswers) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.userPass = userPass;
        this.dob = dob;
        this.securityAnswers = securityAnswers;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public List<SecurityAnswer> getSecurityAnswers() {
        return securityAnswers;
    }

    public void setSecurityAnswers(List<SecurityAnswer> securityAnswers) {
        this.securityAnswers = securityAnswers;
    }

    public String getFName() {
        return fName;
    }

    public Object getLName() {
        return lName;
    }
}
