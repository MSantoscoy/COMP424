package com.example.demo.user;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor // generates the no-args constructor
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence", //
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(name = "id", nullable = false)
    private Long userID;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "pass", nullable = false)
    private String userPass;

    @Column(name = "first_name", nullable = false)
    private String fName;

    @Column(name = "last_name", nullable = false)
    private String lName;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    // Utility method to parse dob from string
    public void setDobFromString(String dobString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dob = LocalDate.parse(dobString, formatter);
    }

    // All-args constructor
    public User(Long userID, String email, String userPass, String fName, String lName, LocalDate dob) {
        this.userID = userID;
        this.email = email;
        this.userPass = userPass;
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
    }

    // Constructor without ID (for insert cases)
    public User(String email, String userPass, String fName, String lName, LocalDate dob) {
        this.email = email;
        this.userPass = userPass;
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
    }

    //  default initialization in handled properly
    @PostLoad
    public void initDefaults() {
        if (this.dob == null) {
            this.dob = LocalDate.of(1900, 1, 1);
        }
        if (this.userID == null) {
            this.userID = -400L;
        }
        if (this.email == null) {
            this.email = "noEmail";
        }
        if (this.userPass == null) {
            this.userPass = "noPass";
        }
        if (this.fName == null) {
            this.fName = "noFName";
        }
        if (this.lName == null) {
            this.lName = "noLName";
        }
    }
}