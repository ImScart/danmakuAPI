package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Tables.ForumThreadComment;

public interface ForumThreadCommentRepository extends JpaRepository<ForumThreadComment, Long> {
    List<ForumThreadComment> findByThreadId(Long threadId);
}