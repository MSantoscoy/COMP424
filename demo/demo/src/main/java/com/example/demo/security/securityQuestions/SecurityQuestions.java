package com.example.demo.security.securityQuestions;

import jakarta.persistence.*;

@Entity
@Table(name = "sec_questions")
public class SecurityQuestions {

    @Id
    @SequenceGenerator(
            name = "question_sequence",
            sequenceName = "question_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_sequence"
    )
    @Column(name = "id", nullable = false)
    private long ID;

    @Column(name = "text", nullable = false)
    private String questionText;

    // No-args constructor (required by JPA)
    public SecurityQuestions() {
    }

    // Constructor for creating new question (without manually setting ID)
    public SecurityQuestions(String questionText) {
        this.questionText = questionText;
    }

    // Full constructor if needed (not recommended with @GeneratedValue, use with caution)
    public SecurityQuestions(long ID, String questionText) {
        this.ID = ID;
        this.questionText = questionText;
    }

    // Getters
    public long getID() {
        return ID;
    }

    public String getQuestionText() {
        return questionText;
    }

    // Setters
    public void setID(long ID) {
        this.ID = ID;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
