package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Exceptions.EmailExistsException;
import com.example.demo.Exceptions.UsernameExistsException;

import jakarta.validation.Valid;

@RestController
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserAccount>> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        UserAccount user = new UserAccount();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(registrationDto.getPassword());
        user.setEmail(registrationDto.getEmail());
        
        userService.registerNewUser(user);
        ApiResponse<UserAccount> response = new ApiResponse<>("0", "Account created successfully");
        
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleUsernameExistsException(UsernameExistsException e) {
        ApiResponse<String> response = new ApiResponse<>("1", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleEmailExistsException(EmailExistsException e) {
        ApiResponse<String> response = new ApiResponse<>("2", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    
}
