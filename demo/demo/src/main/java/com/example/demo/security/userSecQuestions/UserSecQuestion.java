package com.example.demo.security.userSecQuestions;

import com.example.demo.security.securityQuestions.SecurityQuestions;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    public UserSecQuestion(User user, SecurityQuestions question, String answer) {
        this.user = user;
        this.question = question;
        this.answer = answer;
    }

    public UserSecQuestion(Long id, User user, SecurityQuestions question, String answer) {
        this.id = id;
        this.user = user;
        this.question = question;
        this.answer = answer;
    }

    public UserSecQuestion() {

        this.answer = "No answ";
        this.question = null ;
        this.user = null;

    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public SecurityQuestions getQuestion() {
        return question;
    }

    public void setQuestion(SecurityQuestions question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
