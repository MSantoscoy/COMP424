package com.example.demo.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user")
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

    @Column(name="login_count", nullable = true)
    private int loginCount=0;

    @Column(name="last_login", nullable = true)
    private LocalDateTime lastLogin;

    public User(Long userID, String email, String userPass, String fName, String lName, LocalDate dob) {
        this.userID = userID;
        this.email = email;
        this.userPass = userPass;
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
    }

    public User(String email, String encode, String fName, Object lName, LocalDate dob) {
    }

    // Utility method to parse dob from string
    public void setDobFromString(String dobString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dob = LocalDate.parse(dobString, formatter);
    }

    // All-args constructor
    public User(Long userID, String email, String userPass, String fName, String lName, LocalDate dob, int loginCount, LocalDateTime lastLogin) {
        this.userID = userID;
        this.email = email;
        this.userPass = userPass;
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
        this.loginCount = loginCount;
        this.lastLogin = lastLogin;
    }


    // Constructor without ID (for insert cases)
    public User(String email, String userPass, String fName, String lName, LocalDate dob, LocalDateTime lastLogin) {
        this.email = email;
        this.userPass = userPass;
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
        this.loginCount = loginCount;
        this.lastLogin = lastLogin;
    }


    public User() {
        this.email = "noEmail";
        this.userPass = "noPass";
        this.fName = "noFname";
        this.lName = "noLname";
        this.dob = LocalDate.ofEpochDay(1980-01-01);
        this.loginCount=-1;
        this.lastLogin=LocalDateTime.of(1980, 1, 1, 1, 59 );

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

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getlName() {
        return lName;
    }

    public String getfName() {
        return fName;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getEmail() {
        return email;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }


    public String getFName() {
        return this.fName;
    }

    public String getLName() {
        return this.lName;
    }

    public void setFName(String fName) {
        this.fName=fName;
    }

    public void setLName(String lName) {
        this.lName=lName;
    }
}