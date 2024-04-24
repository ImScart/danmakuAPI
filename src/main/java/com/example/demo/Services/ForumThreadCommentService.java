package com.example.demo.Services;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.ForumThreadCommentDto;
import com.example.demo.Exceptions.InvalidIdException;
import com.example.demo.Exceptions.ValuesInvalidException;
import com.example.demo.Repositories.ForumThreadCommentRepository;
import com.example.demo.Repositories.ForumThreadRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Tables.ForumThread;
import com.example.demo.Tables.ForumThreadComment;
import com.example.demo.Tables.UserAccount;

@Service
public class ForumThreadCommentService {

    private final ForumThreadRepository forumThreadRepository;
    private final UserRepository userRepository;
    private final ForumThreadCommentRepository commentRepository;

    public ForumThreadCommentService(ForumThreadRepository forumThreadRepository, UserRepository userRepository,
            ForumThreadCommentRepository commentRepository) {
        this.forumThreadRepository = forumThreadRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public ForumThreadComment createForumThreadComment(ForumThreadCommentDto dto) {
        if (dto.getOwnerId() == null || dto.getOwnerId().toString().isEmpty()
                || dto.getThreadId() == null || dto.getThreadId().toString().isEmpty()
                || dto.getValue() == null || dto.getValue().isEmpty()) {
            throw new ValuesInvalidException("One of the values to comment the thread is invalid.");
        }
        UserAccount userAccount = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new InvalidIdException("User not found with ID: " + dto.getOwnerId()));
        ForumThread forumThread = forumThreadRepository.findById(dto.getThreadId())
                .orElseThrow(
                        () -> new InvalidIdException("Thread not found with ID: " + dto.getThreadId()));
        ForumThreadComment threadComment = new ForumThreadComment();
        threadComment.setOwner(userAccount);
        threadComment.setThread(forumThread);
        threadComment.setCreated(java.time.LocalDateTime.now());
        threadComment.setValue(dto.getValue());
        return commentRepository.save(threadComment);
    }
}
