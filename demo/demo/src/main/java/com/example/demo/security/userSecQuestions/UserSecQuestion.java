package com.example.demo.security.userSecQuestions;

import com.example.demo.security.securityQuestions.SecurityQuestions;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserSecQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private SecurityQuestions question;

    private String answer; //must hash answer

}
