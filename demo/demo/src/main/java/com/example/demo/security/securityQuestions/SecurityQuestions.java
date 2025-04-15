package com.example.demo.security.securityQuestions;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sec_questions")
@NoArgsConstructor
public class SecurityQuestions {

    @Id
    @SequenceGenerator(
            name = "question_sequence",
            sequenceName = "question_sequence", //
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_sequence"
    )
    @Column(name = "id", nullable = false)
    private long ID;
    private String questionText;
}
