package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Tables.ForumThread;

public interface ForumThreadRepository extends JpaRepository<ForumThread, Long> {
}