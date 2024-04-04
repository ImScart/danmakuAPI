package com.example.demo.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.ForumThreadRepository;
import com.example.demo.UserRepository;
import com.example.demo.DTO.ForumThreadCreateDto;
import com.example.demo.Exceptions.InvalidIdException;
import com.example.demo.Tables.ForumThread;
import com.example.demo.Tables.UserAccount;

@Service
public class ForumThreadService {

    private final ForumThreadRepository threadRepository;
    private final UserRepository userRepository;

    @Autowired
    public ForumThreadService(ForumThreadRepository threadRepository, UserRepository userRepository) {
        this.threadRepository = threadRepository;
        this.userRepository = userRepository;
    }

    public ForumThread createThread(ForumThreadCreateDto threadCreateDto) {
        UserAccount userAccount = userRepository.findById(threadCreateDto.getOwnerId())
                .orElseThrow(() -> new InvalidIdException("User not found with ID: " + threadCreateDto.getOwnerId()));

        ForumThread thread = new ForumThread();
        thread.setTitle(threadCreateDto.getTitle());
        thread.setValue(threadCreateDto.getValue());
        thread.setCreated(java.time.LocalDateTime.now());
        thread.setUserAccount(userAccount);

        return threadRepository.save(thread);
    }
}
