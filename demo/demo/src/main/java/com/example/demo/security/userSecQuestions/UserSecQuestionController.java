package com.example.demo.security.userSecQuestions;

import com.example.demo.dto.UserSecurityQuestionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-security-questions")
public class UserSecQuestionController {

    private final UserSecQuestionService service;

    public UserSecQuestionController(UserSecQuestionService service) {
        this.service = service;
    }

    @PostMapping("/{userId}/save")
    public ResponseEntity<String> saveAnswers(
            @PathVariable Long userId,
            @RequestBody List<UserSecurityQuestionRequest> requests) {
        service.saveUserAnswers(userId, requests);
        return ResponseEntity.ok("Security answers saved.");
    }

    @PostMapping("/{userId}/verify")
    public ResponseEntity<String> verifyAnswers(
            @PathVariable Long userId,
            @RequestBody List<UserSecurityQuestionRequest> requests) {
        boolean verified = service.verifyUserAnswers(userId, requests);
        return verified
                ? ResponseEntity.ok("Verification successful.")
                : ResponseEntity.status(403).body("Verification failed.");
    }
}
