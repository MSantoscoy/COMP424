package com.example.demo.security.securityQuestions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


    @RestController//Tells Spring this class handles HTTP requests
    @RequestMapping("/api/v1/questions") // Optional base path for all routes here
    public class SecurityQuestionsController {

        private final SecurityQuestionsService service;

        public SecurityQuestionsController(SecurityQuestionsService service) {
            this.service = service;
        }

        @GetMapping
        public List<SecurityQuestions> getAllQuestions() {
            return service.getAllQuestions();
        }
    }



