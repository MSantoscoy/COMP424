package com.example.demo.user;
import com.example.demo.dto.*;
import com.example.demo.security.securityQuestions.SecurityQuestions;
import com.example.demo.security.securityQuestions.SecurityQuestionsRepository;
import com.example.demo.security.userSecQuestions.UserSecQuestion;
import com.example.demo.security.userSecQuestions.UserSecQuestionRepository;
import com.example.demo.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityQuestionsRepository securityQuestionsRepo;

    @Autowired
    private UserSecQuestionRepository userSecQuestionRepo;

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

        user.setLoginCount(user.getLoginCount() + 1);
        user.setLastLogin(LocalDateTime.now());

        userRepository.save(user);

        return tokenService.generateToken(user.getEmail());
    }

    public UserResponse getCurrentUser() {
        // Get email from token (in real app, from SecurityContextHolder or token service)
        String email = tokenService.extractCurrentUserEmail(); // Create this helper if needed
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new UserResponse(user.getEmail(), user.getFName(), user.getLName(), user.getLoginCount(), user.getLastLogin());
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

    public void register(RegisterRequest request) {
        if (userRepository.findByEmailIgnoreCase(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        validatePass(request.getUserPass());

        // Create and save user
        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getUserPass()),
                request.getFName(),
                (String) request.getLName(),
                request.getDob()
        );

        userRepository.save(user);

        // Link security questions
        List<UserSecQuestion> questionLinks = request.getSecurityAnswers().stream()
                .map(ans -> {
                    SecurityQuestions question = securityQuestionsRepo.findById(ans.getQuestionId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid security question ID"));
                    return new UserSecQuestion(user, question, passwordEncoder.encode(ans.getAnswer()));
                })
                .toList();

        userSecQuestionRepo.saveAll(questionLinks);
    }

    public boolean verifySecurityAnswers(String email, List<SecurityAnswer> submittedAnswers) {
        // Step 1: Find user by email
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Step 2: Fetch all security questions and hashed answers linked to this user
        List<UserSecQuestion> storedAnswers = userSecQuestionRepo.findByUser(user);

        // Step 3: Check each submitted answer against stored hash
        for (SecurityAnswer submitted : submittedAnswers) {
            // Find matching stored answer
            UserSecQuestion match = storedAnswers.stream()
                    .filter(sa -> sa.getQuestion().getID() == submitted.getQuestionId())
                    .findFirst()
                    .orElse(null);

            // If question not found or answer mismatch → fail
            if (match == null || !passwordEncoder.matches(submitted.getAnswer(), match.getAnswer())) {
                return false;
            }
        }

        // All answers matched
        return true;
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
    public void updateProfile(UserRequest request) {
        String currentEmail = tokenService.extractCurrentUserEmail();

        User user = userRepository.findByEmailIgnoreCase(currentEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getFName() != null && !request.getFName().isBlank()) {
            user.setFName(request.getFName());
        }

        if (request.getLName() != null && !request.getLName().isBlank()) {
            user.setLName(request.getLName());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }

        userRepository.save(user);
    }



}