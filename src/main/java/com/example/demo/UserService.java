package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.EmailExistsException;
import com.example.demo.Exceptions.UsernameExistsException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserAccount registerNewUser(UserAccount user) {
        Optional<UserAccount> existingUser = userRepository.findByUsername(user.getUsername());
        Optional<UserAccount> existingEmail = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new UsernameExistsException("Username already exists.");
        }
        if (existingEmail.isPresent()) {
            throw new EmailExistsException("Email already exists.");
        }
        user.setPassword(user.getPassword());
        
        return userRepository.save(user);
    }
}
