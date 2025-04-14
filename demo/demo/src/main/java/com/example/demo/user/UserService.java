package com.example.demo.user;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // special characters needed for password validation !@#$%^&*
   static String specialChars = ".*[!@#$%^&*].*";
   static int minLen = 8;

    public boolean passMinLen(String pass)
    {
        return pass.length() >= 8;
    }

    public boolean hasSpecChar(String pass)
    {
        return pass.matches(specialChars);
    }

//    @Autowired
//    private PasswordEncoder passwordEncoder;

}