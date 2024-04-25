package com.example.demo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.CreateScoreDto;
import com.example.demo.DTO.UserTotalScoreDto;
import com.example.demo.Exceptions.InvalidIdException;
import com.example.demo.Exceptions.ValuesInvalidException;
import com.example.demo.Repositories.MapRepository;
import com.example.demo.Repositories.ScoreRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Tables.Map;
import com.example.demo.Tables.Score;
import com.example.demo.Tables.UserAccount;

@Service
public class ScoreService {

    private final MapRepository mapRepository;
    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;

    public ScoreService(MapRepository mapRepository, UserRepository userRepository, ScoreRepository scoreRepository) {
        this.mapRepository = mapRepository;
        this.userRepository = userRepository;
        this.scoreRepository=scoreRepository;
    }

    public Score createScore(CreateScoreDto dto) {
        if (dto.getOwnerId() == null || dto.getOwnerId().toString().isEmpty()
                || dto.getMapId()== null || dto.getMapId().toString().isEmpty()
                || dto.getValue() == null || dto.getValue().toString().isEmpty()) {
            throw new ValuesInvalidException("One of the values to create score is invalid.");
        }
        UserAccount userAccount = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new InvalidIdException("User not found with ID: " + dto.getOwnerId()));
        Map map = mapRepository.findById(dto.getMapId())
                .orElseThrow(
                        () -> new InvalidIdException("Map not found with ID: " + dto.getMapId()));
        Score score = new Score();
        score.setOwner(userAccount);
        score.setMap(map);
        score.setCreated(java.time.LocalDateTime.now());
        score.setValue(dto.getValue());
        return scoreRepository.save(score);
    }

    public List<UserTotalScoreDto> getTotalScoresForEachUser() {
        return scoreRepository.findTotalScoresForEachUser();
    }
}
