package com.example.demo.security.securityQuestions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityQuestionsRepository extends JpaRepository<SecurityQuestions, Long> {

}