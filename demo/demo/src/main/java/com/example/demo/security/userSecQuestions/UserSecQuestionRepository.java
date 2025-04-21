package com.example.demo.security.userSecQuestions;

import com.example.demo.security.securityQuestions.SecurityQuestions;
import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSecQuestionRepository extends JpaRepository<UserSecQuestion, Long> {
    List<UserSecQuestion> findByUser(User user);
    Optional<UserSecQuestion> findByUserAndQuestion(User user, SecurityQuestions question);
}
