package com.example.demo.security.securityQuestions;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityQuestionsService {
    private final SecurityQuestionsRepository repository;

    public SecurityQuestionsService(SecurityQuestionsRepository repository) {
        this.repository = repository;
    }

    public List<SecurityQuestions> getAllQuestions() {
        return repository.findAll();
    }
}
