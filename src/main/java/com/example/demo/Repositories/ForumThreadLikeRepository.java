package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Tables.ForumThreadLike;

public interface ForumThreadLikeRepository extends JpaRepository<ForumThreadLike, Long> {
    boolean existsByUserAccount_IdAndForumThread_Id(Long userAccountId, Long forumThreadId);

    List<ForumThreadLike> findAllByForumThread_Id(Long forumThreadId);

    boolean existsById(Long id);
}
