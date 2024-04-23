package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Tables.ForumThreadLike;

public interface ForumThreadLikeRepository extends JpaRepository<ForumThreadLike, Long> {
    boolean existsByUserAccount_IdAndForumThreadID_Id(Long userAccountId, Long forumThreadId);
}
