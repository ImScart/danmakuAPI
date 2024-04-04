package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.UserBioDto;
import com.example.demo.DTO.UserRegistrationDto;
import com.example.demo.Exceptions.EmailExistsException;
import com.example.demo.Exceptions.InvalidPasswordException;
import com.example.demo.Exceptions.UsernameExistsException;
import com.example.demo.Exceptions.UsernameNotFoundException;
import com.example.demo.Tables.UserAccount;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserAccount registerNewUser(UserRegistrationDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameExistsException("Username already exists.");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailExistsException("Email already exists.");
        }
        UserAccount user = new UserAccount(dto.getUsername(), dto.getPassword(), dto.getEmail());
        return userRepository.save(user);
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
}
