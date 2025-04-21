package com.example.demo.security.securityQuestions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //Tells Spring this class handles HTTP requests
@RequestMapping("/api/v1/security-questions") // Optional base path for all routes here
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
