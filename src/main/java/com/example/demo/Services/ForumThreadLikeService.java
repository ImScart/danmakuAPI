package com.example.demo.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.ForumThreadLikeDto;
import com.example.demo.DTO.ForumThreadLikesDto;
import com.example.demo.Exceptions.InvalidIdException;
import com.example.demo.Exceptions.ThreadAlreadyLikedByUserException;
import com.example.demo.Exceptions.ValuesInvalidException;
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

    public ForumThreadLikeService(ForumThreadRepository forumThreadRepository, UserRepository userRepository,
            ForumThreadLikeRepository forumThreadLikeRepository) {
        this.forumThreadRepository = forumThreadRepository;
        this.userRepository = userRepository;
        this.forumThreadLikeRepository = forumThreadLikeRepository;
    }

    public void likeThread(Long userId, Long threadId) {
        if (forumThreadLikeRepository.existsByUserAccount_IdAndForumThread_Id(userId, threadId)) {
            throw new ThreadAlreadyLikedByUserException("User has already liked the thread");
        }
    }

    public ForumThreadLike createForumThreadLike(ForumThreadLikeDto threadLikeDto) {
        if (threadLikeDto.getOwnerId() == null || threadLikeDto.getOwnerId().toString().isEmpty()
                || threadLikeDto.getForumThreadId() == null || threadLikeDto.getForumThreadId().toString().isEmpty()) {
            throw new ValuesInvalidException("One of the values to like the thread is invalid.");
        }
        UserAccount userAccount = userRepository.findById(threadLikeDto.getOwnerId())
                .orElseThrow(() -> new InvalidIdException("User not found with ID: " + threadLikeDto.getOwnerId()));
        ForumThread forumThread = forumThreadRepository.findById(threadLikeDto.getForumThreadId())
                .orElseThrow(
                        () -> new InvalidIdException("Thread not found with ID: " + threadLikeDto.getForumThreadId()));
        ForumThreadLike threadLike = new ForumThreadLike();
        threadLike.setUserAccount(userAccount);
        threadLike.setForumThread(forumThread);

        return forumThreadLikeRepository.save(threadLike);
    }

    public List<ForumThreadLikeDto> getLikesByThreadId(ForumThreadLikesDto dto) {
        if(dto.getForumThreadId()==null||dto.getForumThreadId().toString().isEmpty())
        {
            throw new InvalidIdException("Thread not found with ID: " + dto.getForumThreadId());
        }
        List<ForumThreadLike> threadsLikes = forumThreadLikeRepository.findAllByForumThread_Id(dto.getForumThreadId());
        List<ForumThreadLikeDto> threadLikeDTOs = new ArrayList<>();

        for (ForumThreadLike threadLike : threadsLikes) {
            ForumThreadLikeDto threadLikeDto = new ForumThreadLikeDto(
                    threadLike.getId(),
                    threadLike.getUserAccount().getId(),
                    threadLike.getForumThread().getId());

            threadLikeDTOs.add(threadLikeDto);
        }

        return threadLikeDTOs;
    }

}
