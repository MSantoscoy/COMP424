package com.example.demo.user;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // special characters needed for password validation !@#$%^&*
   static String specialChars = ".*[!@#$%^&*].*";
   static int minLen = 8;


    public void hasMinLen(String pass)
    {
        if(!(pass.length() >= minLen))
        {throw new IllegalArgumentException("Password must be at least 8 characters long.");}
    }

    public void hasSpecChar(String pass)
    {
        if(!pass.matches(specialChars))
        {
            throw new IllegalArgumentException("Password needs one of the following: !,@,#,$,%,^,&,*");
        };
    }

    private void hasUppercase(String password) {
        if(!password.matches(".*[A-Z].*"))
        {
            throw new IllegalArgumentException("Password needs one uppercase letter!");
        }
    }

    private void hasLowercase(String password) {
        if(!password.matches(".*[a-z].*"))
        {
            throw new IllegalArgumentException("Password needs one lowercase letter!");
        }
    }

    private void hasNum(String password)
    {
        if(!password.matches(".*[0-9].*"))
        {
            throw new IllegalArgumentException("Password needs one number!");
        }
    }

    private void validatePass(String password)
    {
        hasUppercase(password);
        hasLowercase(password);
        hasNum(password);
        hasSpecChar(password);
        hasMinLen(password);
    }

    public void register(User user)
    {

        // Check if user with this email already exists
        if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        //Check if valid password, otherwise exit code black
        validatePass(user.getUserPass());

        //encode password
           user.setUserPass( passwordEncoder.encode(user.getUserPass()));

           //save user
        userRepository.save(user);
    }

    public void changePassword(User user)
    {

    }

}