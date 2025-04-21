package com.example.demo.user;
import com.example.demo.dto.ChangePasswordRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.token.TokenService;
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

    @Autowired
    private final TokenService tokenService;

    public UserService(PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public void updateUser(Long id, User updatedUser) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        existing.setFName(updatedUser.getFName());
        existing.setLName(updatedUser.getLName());
        existing.setEmail(updatedUser.getEmail()); // optional

        userRepository.save(existing);
    }


    public String login(LoginRequest request) {
        User user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getUserPass())) {
            throw new IllegalArgumentException("Invalid email or password");
        }



        return tokenService.generateToken(user.getEmail());
    }

    public UserResponse getCurrentUser() {
        // Get email from token (in real app, from SecurityContextHolder or token service)
        String email = tokenService.extractCurrentUserEmail(); // Create this helper if needed
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new UserResponse(user.getEmail(), user.getFName(), user.getLName());
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

    public void changePassword(ChangePasswordRequest request) {
        User user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getUserPass())) {
            throw new IllegalArgumentException("Incorrect current password");
        }

        validatePass(request.getNewPassword());
        user.setUserPass(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }


}