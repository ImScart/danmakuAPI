package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private final UserRepository userRepository;

    
    public void registerNewUser(UserAccount user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) 
        {
            throw new IllegalStateException("Username already exists.");
        }
        user.setPassword(user.getPassword());
        
        userRepository.save(user);
    }
}
