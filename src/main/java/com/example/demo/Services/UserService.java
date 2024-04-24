package com.example.demo.Services;

import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.ResetPasswordDto;
import com.example.demo.DTO.ResetPasswordEmailDto;
import com.example.demo.DTO.UserBioDto;
import com.example.demo.DTO.UserLoginDto;
import com.example.demo.DTO.UserRegistrationDto;
import com.example.demo.Exceptions.EmailExistsException;
import com.example.demo.Exceptions.EmailIsNotVerifiedException;
import com.example.demo.Exceptions.ExpiredOrInvalidTokenException;
import com.example.demo.Exceptions.InvalidEmailException;
import com.example.demo.Exceptions.InvalidIdException;
import com.example.demo.Exceptions.InvalidPasswordException;
import com.example.demo.Exceptions.InvalidResetTokenException;
import com.example.demo.Exceptions.UsernameExistsException;
import com.example.demo.Exceptions.UsernameNotFoundException;
import com.example.demo.Exceptions.ValuesInvalidException;
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
        if (dto.getUsername() == null || dto.getUsername().isEmpty() || dto.getPassword() == null
                || dto.getPassword().isEmpty() || dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new ValuesInvalidException("One of the values to register the user is invalid.");
        }
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

        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        UserAccount savedUser = userRepository.save(user);

        sendVerificationEmail(user.getEmail(), verificationToken);

        return savedUser;
    }

    public void setResetToken(String email) {
        UserAccount user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidEmailException("Email not found"));
        if (!user.getEmailIsVerified()) {
            throw new EmailIsNotVerifiedException("Email is not verified.");
        }
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);
    }

    public UserAccount loginUser(UserLoginDto dto) {
        if (dto.getUsername() == null || dto.getUsername().isEmpty() || dto.getPassword() == null
                || dto.getPassword().isEmpty()) {
            throw new ValuesInvalidException("One of the values to login the user is invalid.");
        }
        UserAccount user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!dto.getPassword().equals(user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        return user;
    }

    public void updateUserBio(UserBioDto dto) {
        if (dto.getUsername() == null || dto.getUsername().isEmpty() || dto.getPassword() == null
                || dto.getPassword().isEmpty() || dto.getBio() == null || dto.getBio().isEmpty()) {
            throw new ValuesInvalidException("One of the values to change the users bio is invalid.");
        }
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
        String verificationUrl = "http://144.217.83.146:8080/user/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("danmakuverification@outlook.com");
        message.setTo(email);
        message.setSubject("Email Verification");
        message.setText("To verify your email, please click the link below:\n" + verificationUrl);

        mailSender.send(message);
    }

    public void sendResetEmail(ResetPasswordEmailDto dto, String token) {
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new ValuesInvalidException("One of the values to send the email is invalid.");
        }
        String verificationUrl = "http://144.217.83.146/resetPassword.html?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("danmakuverification@outlook.com");
        message.setTo(dto.getEmail());
        message.setSubject("Reset your password");
        message.setText("To reset your password, please click the link below:\n" + verificationUrl);

        mailSender.send(message);
    }

    public void updateUserPassword(ResetPasswordDto dto) {
        if (dto.getToken() == null || dto.getToken().isEmpty() || dto.getPassword() == null
                || dto.getPassword().isEmpty()) {
            throw new ValuesInvalidException("One of the values to change the users password is invalid.");
        }
        UserAccount user = userRepository.findByResetToken(dto.getToken())
                .orElseThrow(() -> new InvalidResetTokenException("Invalid reset token"));

        user.setPassword(dto.getPassword());
        userRepository.save(user);
    }

    public UserAccount getUserByVerificationToken(String token) {
        return userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new ExpiredOrInvalidTokenException("Invalid or expired verification token"));
    }

    public UserAccount getUserByID(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Invalid ID"));
    }

    public UserAccount getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidEmailException("Invalid Email"));
    }

    public UserAccount getUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken)
                .orElseThrow(() -> new InvalidResetTokenException("Invalid Reset Token"));
    }
}