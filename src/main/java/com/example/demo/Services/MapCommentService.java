package com.example.demo.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.CreateMapCommentDto;
import com.example.demo.DTO.MapCommentsDto;
import com.example.demo.Exceptions.InvalidIdException;
import com.example.demo.Exceptions.ValuesInvalidException;
import com.example.demo.Repositories.MapCommentRepository;
import com.example.demo.Repositories.MapRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Tables.Map;
import com.example.demo.Tables.MapComment;
import com.example.demo.Tables.UserAccount;

@Service
public class MapCommentService {

    private final MapRepository mapRepository;
    private final UserRepository userRepository;
    private final MapCommentRepository commentRepository;

    public MapCommentService(MapRepository mapRepository, UserRepository userRepository,
            MapCommentRepository commentRepository) {
        this.mapRepository = mapRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public MapComment createMapComment(CreateMapCommentDto dto) {
        if (dto.getOwnerId() == null || dto.getOwnerId().toString().isEmpty()
                || dto.getMapId()== null || dto.getMapId().toString().isEmpty()
                || dto.getValue() == null || dto.getValue().isEmpty()) {
            throw new ValuesInvalidException("One of the values to comment the map is invalid.");
        }
        UserAccount userAccount = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new InvalidIdException("User not found with ID: " + dto.getOwnerId()));
        Map map = mapRepository.findById(dto.getMapId())
                .orElseThrow(
                        () -> new InvalidIdException("Map not found with ID: " + dto.getMapId()));
        MapComment mapComment = new MapComment();
        mapComment.setOwner(userAccount);
        mapComment.setMap(map);
        mapComment.setCreated(java.time.LocalDateTime.now());
        mapComment.setValue(dto.getValue());
        return commentRepository.save(mapComment);
    }

    public List<CreateMapCommentDto> getCommentsByMapId(MapCommentsDto dto) {
        if(dto.getMapId()==null||dto.getMapId().toString().isEmpty())
        {
            throw new InvalidIdException("Map not found with ID: " + dto.getMapId());
        }
        List<MapComment> mapComments = commentRepository.findByMapId(dto.getMapId());
        List<CreateMapCommentDto> MapCommentDTOs = new ArrayList<>();

        for (MapComment mapComment : mapComments) {
            CreateMapCommentDto threadCommentDto = new CreateMapCommentDto(
                    mapComment.getOwner().getId(),
                    mapComment.getOwner().getUsername(),
                    mapComment.getOwner().getProfilePicture(),
                    mapComment.getMap().getId(),
                    mapComment.getValue());

            MapCommentDTOs.add(threadCommentDto);
        }

        return MapCommentDTOs;
    }
}
