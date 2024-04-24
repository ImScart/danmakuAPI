package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Tables.ForumThreadComment;

public interface ForumThreadCommentRepository extends JpaRepository<ForumThreadComment, Long> {

}