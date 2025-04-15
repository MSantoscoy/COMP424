package com.example.demo.security.securityQuestions;

import java.util.List;

public class SecurityQuestionsService {
    private final SecurityQuestionsRepository repository;

    public SecurityQuestionsService(SecurityQuestionsRepository repository) {
        this.repository = repository;
    }

    public List<SecurityQuestions> getAllQuestions() {
        return repository.findAll();
    }
}
