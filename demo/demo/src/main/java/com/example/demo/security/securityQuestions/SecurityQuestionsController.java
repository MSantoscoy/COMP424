package com.example.demo.security.securityQuestions;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class SecurityQuestionsController {
    private final SecurityQuestionsService questionService;

    public SecurityQuestionsController(SecurityQuestionsService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<SecurityQuestions> getAllQuestions() {
        return questionService.getAllQuestions();
    }
}
