package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Tables.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByMapId(Long mapId);
    List<Score> findByOwnerId(Long mapId);
}