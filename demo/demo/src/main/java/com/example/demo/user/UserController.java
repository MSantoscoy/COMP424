package com.example.demo.user;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserResponse;
import com.nimbusds.oauth2.sdk.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Register new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.register(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    //Get current user
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        UserResponse userResponse = userService.getCurrentUser();
        return ResponseEntity.ok(userResponse);
    }

    //Update profile
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user);
        return ResponseEntity.ok("User updated successfully!");
    }

    //Change password
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok("Password changed successfully!");
    }

    //Delete account
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.ok("User deleted successfully!");
//    }
}



