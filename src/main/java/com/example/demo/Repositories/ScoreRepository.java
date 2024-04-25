package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.DTO.UserTotalScoreDto;
import com.example.demo.Tables.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByMapId(Long mapId);
    List<Score> findByOwnerId(Long mapId);
    @Query("SELECT new com.example.demo.DTO.UserTotalScoreDto(s.owner.id, s.owner.username, SUM(s.value)) FROM Score s GROUP BY s.owner.id, s.owner.username")
    List<UserTotalScoreDto> findTotalScoresForEachUser();
    
}