package com.example.demo.security.securityQuestions;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SecurityQuestionsConfig {

    @Bean
    CommandLineRunner commandLineRunner(SecurityQuestionsRepository repository) {
        return args -> {
            SecurityQuestions questionOne = new SecurityQuestions("What is your mother's maiden name?");
            SecurityQuestions questionTwo = new SecurityQuestions("What was your first childhood pet?");
            SecurityQuestions questionThree = new SecurityQuestions("What is the name of your best friend in college?");
            SecurityQuestions questionFour = new SecurityQuestions("What is your first grade teacher's name?");
            SecurityQuestions questionFive = new SecurityQuestions("What is your favorite comfort snack?");

            repository.saveAll(List.of(
                    questionOne,
                    questionTwo,
                    questionThree,
                    questionFour,
                    questionFive
            ));
        };
    }
}
