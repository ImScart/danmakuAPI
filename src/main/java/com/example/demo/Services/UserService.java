package com.example.demo.Services;

import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.UserBioDto;
import com.example.demo.DTO.UserLoginDto;
import com.example.demo.DTO.UserRegistrationDto;
import com.example.demo.Exceptions.EmailExistsException;
import com.example.demo.Exceptions.ExpiredOrInvalidTokenException;
import com.example.demo.Exceptions.InvalidIdException;
import com.example.demo.Exceptions.InvalidPasswordException;
import com.example.demo.Exceptions.UsernameExistsException;
import com.example.demo.Exceptions.UsernameNotFoundException;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Tables.UserAccount;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    public UserService(UserRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public UserAccount registerNewUser(UserRegistrationDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameExistsException("Username already exists.");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailExistsException("Email already exists.");
        }

        UserAccount user = new UserAccount();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        UserAccount savedUser = userRepository.save(user);

        sendVerificationEmail(user.getEmail(), token);

        return savedUser;
    }

    public UserAccount loginUser(UserLoginDto dto) {
        UserAccount user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!dto.getPassword().equals(user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        return user;
    }

    public void updateUserBio(UserBioDto dto) {
        UserAccount user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!dto.getPassword().equals(user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        user.setBio(dto.getBio());
        userRepository.save(user);
    }

    public UserAccount saveUser(UserAccount user) {
        return userRepository.save(user);
    }

    private void sendVerificationEmail(String email, String token) {
        String verificationUrl = "http://127.0.0.1:8080/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("danmakuverification@outlook.com");
        message.setTo(email);
        message.setSubject("Email Verification");
        message.setText("To verify your email, please click the link below:\n" + verificationUrl);

        mailSender.send(message);
    }

    public UserAccount getUserByVerificationToken(String token) {
        return userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new ExpiredOrInvalidTokenException("Invalid or expired verification token"));
    }

    public UserAccount getUserByID(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Invalid ID"));
    }

}