package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Tables.MapComment;

public interface MapCommentRepository extends JpaRepository<MapComment, Long> {
    List<MapComment> findByMapId(Long mapId);
}