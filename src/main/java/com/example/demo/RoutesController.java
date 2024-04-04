package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ForumThreadCreateDto;
import com.example.demo.DTO.ForumThreadDto;
import com.example.demo.DTO.UserBioDto;
import com.example.demo.DTO.UserLoginDto;
import com.example.demo.DTO.UserRegistrationDto;
import com.example.demo.Exceptions.EmailExistsException;
import com.example.demo.Exceptions.ExpiredOrInvalidTokenException;
import com.example.demo.Exceptions.InvalidIdException;
import com.example.demo.Exceptions.InvalidPasswordException;
import com.example.demo.Exceptions.UsernameExistsException;
import com.example.demo.Exceptions.UsernameNotFoundException;
import com.example.demo.Services.ForumThreadService;
import com.example.demo.Services.UserService;
import com.example.demo.Tables.ForumThread;
import com.example.demo.Tables.UserAccount;

import jakarta.validation.Valid;

@RestController
public class RoutesController {

    private final UserService userService;
    private final ForumThreadService threadService;

    @Autowired
    public RoutesController(UserService userService, ForumThreadService threadService) {
        this.userService = userService;
        this.threadService = threadService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserAccount>> registerUser(
            @Valid @RequestBody UserRegistrationDto registrationDto) {
        UserAccount user = userService.registerNewUser(registrationDto);
        ApiResponse<UserAccount> response = new ApiResponse<>("0", "Account created successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<UserAccount>> loginUser(
            @Valid @RequestBody UserLoginDto loginDto) {
        UserAccount user = userService.loginUser(loginDto);
        ApiResponse<UserAccount> response = new ApiResponse<>("0", user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/updateBio")
    public ResponseEntity<ApiResponse<String>> updateBio(@RequestBody UserBioDto request) {
        userService.updateUserBio(request);
        ApiResponse<String> response = new ApiResponse<>("0", "Bio updated successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        UserAccount user = userService.getUserByVerificationToken(token);

        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid or expired verification token");
        }

        user.setEmailIsVerified(true);
        userService.saveUser(user);

        return ResponseEntity.ok("Email verified successfully");
    }

    @PostMapping("/thread/create")
    public ResponseEntity<ForumThread> createThread(@RequestBody ForumThreadCreateDto threadCreateDto) {
        ForumThread thread = threadService.createThread(threadCreateDto);
        return ResponseEntity.ok(thread);
    }
    @GetMapping("/thread/all")
    public ResponseEntity<List<ForumThreadDto>> getAllThreads() {
        List<ForumThreadDto> threads = threadService.getAllThreads();
        return ResponseEntity.ok().body(threads);
    }

    // USERNAME IN USE
    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleUsernameExistsException(UsernameExistsException e) {
        ApiResponse<String> response = new ApiResponse<>("1", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // EMAIL IN USE
    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleEmailExistsException(EmailExistsException e) {
        ApiResponse<String> response = new ApiResponse<>("2", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // INVALID USERNAME
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ApiResponse<String> response = new ApiResponse<>("1", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // INVALID PASSWORD
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidPasswordException(InvalidPasswordException e) {
        ApiResponse<String> response = new ApiResponse<>("2", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // INVALID TOKEN
    @ExceptionHandler(ExpiredOrInvalidTokenException.class)
    public ResponseEntity<ApiResponse<String>> handleExpiredOrInvalidTokenException(ExpiredOrInvalidTokenException e) {
        ApiResponse<String> response = new ApiResponse<>("1", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // INVALID ID
    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidIdException(InvalidIdException e) {
        ApiResponse<String> response = new ApiResponse<>("1", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
