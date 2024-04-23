package com.example.demo.Services;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.ForumThreadLikeDto;
import com.example.demo.Exceptions.InvalidIdException;
import com.example.demo.Exceptions.ThreadAlreadyLikedByUserException;
import com.example.demo.Exceptions.ThreadValuesInvalidException;
import com.example.demo.Repositories.ForumThreadLikeRepository;
import com.example.demo.Repositories.ForumThreadRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Tables.ForumThread;
import com.example.demo.Tables.ForumThreadLike;
import com.example.demo.Tables.UserAccount;

@Service
public class ForumThreadLikeService {

    private final ForumThreadRepository forumThreadRepository;
    private final ForumThreadLikeRepository forumThreadLikeRepository;
    private final UserRepository userRepository;

    public ForumThreadLikeService(ForumThreadRepository forumThreadRepository, UserRepository userRepository,ForumThreadLikeRepository forumThreadLikeRepository) {
        this.forumThreadRepository = forumThreadRepository;
        this.userRepository = userRepository;
        this.forumThreadLikeRepository=forumThreadLikeRepository;
    }

    public void likeThread(Long userId, Long threadId) {
        if (forumThreadLikeRepository.existsByUserAccount_IdAndForumThreadID_Id(userId, threadId)) {
            throw new ThreadAlreadyLikedByUserException("User has already liked the thread");
        }
    }

    public ForumThreadLike createForumThreadLike(ForumThreadLikeDto threadLikeDto) {
        UserAccount userAccount = userRepository.findById(threadLikeDto.getOwnerId())
                .orElseThrow(() -> new InvalidIdException("User not found with ID: " + threadLikeDto.getOwnerId()));
        ForumThread forumThread = forumThreadRepository.findById(threadLikeDto.getForumThreadId())
                .orElseThrow(() -> new InvalidIdException("Thread not found with ID: " + threadLikeDto.getForumThreadId()));
        if (threadLikeDto.getForumThreadId().toString().isEmpty()) {
            throw new ThreadValuesInvalidException("One of the values to like the thread is invalid.");
        }
        ForumThreadLike threadLike = new ForumThreadLike();
        threadLike.setUserAccount(userAccount);
        threadLike.setForumThreadID(forumThread);

        return forumThreadLikeRepository.save(threadLike);
    }
}
