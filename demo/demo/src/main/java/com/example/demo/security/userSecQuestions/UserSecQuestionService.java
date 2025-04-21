package com.example.demo.security.userSecQuestions;

import com.example.demo.dto.SecurityAnswer;
import com.example.demo.security.securityQuestions.SecurityQuestions;
import com.example.demo.security.securityQuestions.SecurityQuestionsRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSecQuestionService {

    private final UserRepository userRepository;
    private final SecurityQuestionsRepository questionRepository;
    private final UserSecQuestionRepository userSecQuestionRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSecQuestionService(UserRepository userRepository,
                                  SecurityQuestionsRepository questionRepository,
                                  UserSecQuestionRepository userSecQuestionRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.userSecQuestionRepository = userSecQuestionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUserAnswers(Long userId, List<SecurityAnswer> requests) {
        User user = userRepository.findById(userId).orElseThrow();

        for (SecurityAnswer req : requests) {
            SecurityQuestions question = questionRepository.findById(req.getQuestionId()).orElseThrow();

            UserSecQuestion usq = new UserSecQuestion();
            usq.setUser(user);
            usq.setQuestion(question);
            usq.setAnswer(passwordEncoder.encode(req.getAnswer()));

            userSecQuestionRepository.save(usq);
        }
    }

    public boolean verifyUserAnswers(Long userId, List<SecurityAnswer> requests) {
        User user = userRepository.findById(userId).orElseThrow();

        for (SecurityAnswer req : requests) {
            SecurityQuestions question = questionRepository.findById(req.getQuestionId()).orElseThrow();

            UserSecQuestion usq = userSecQuestionRepository
                    .findByUserAndQuestion(user, question)
                    .orElse(null);

            if (usq == null || !passwordEncoder.matches(req.getAnswer(), usq.getAnswer())) {
                return false;
            }
        }
        return true;
    }
}
