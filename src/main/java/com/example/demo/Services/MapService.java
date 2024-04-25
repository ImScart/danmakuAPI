package com.example.demo.Services;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.CreateMapDto;
import com.example.demo.DTO.MapsDto;
import com.example.demo.Exceptions.InvalidIdException;
import com.example.demo.Exceptions.ValuesInvalidException;
import com.example.demo.Repositories.MapRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Tables.Map;
import com.example.demo.Tables.Map.Difficulty;
import com.example.demo.Tables.UserAccount;

@Service
public class MapService {

    private final UserRepository userRepository;
    private final MapRepository mapRepository;

    public MapService(UserRepository userRepository,MapRepository mapRepository) {
        this.userRepository = userRepository;
        this.mapRepository=mapRepository;
    }

    public Map uploadNewMap(CreateMapDto dto) {
        if (dto.getOwnerID() == null || dto.getOwnerID().toString().isEmpty() || dto.getName() == null
                || dto.getName().isEmpty() || dto.getFile() == null || dto.getFile().isEmpty()
                || dto.getDifficulty() == null || dto.getDifficulty().isEmpty()) {
            throw new ValuesInvalidException("One of the values to register the user is invalid.");
        }
        UserAccount userAccount = userRepository.findById(dto.getOwnerID())
                .orElseThrow(() -> new InvalidIdException("User not found with ID: " + dto.getOwnerID()));

        Map map = new Map();
        map.setOwner(userAccount);
        map.setName(dto.getName());
        map.setDifficulty(Difficulty.valueOf(dto.getDifficulty()));
        map.setIsAdminVerified(false);
        Map savedMap = mapRepository.save(map);

        return savedMap;
    }

    public void updateMapPath(Integer id) {
        if (id == null || id.toString().isEmpty()) {
            throw new ValuesInvalidException("One of the values to upload a map is invalid.");
        }
        Map map = mapRepository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Map not found"));

        map.setDownloadUrl("http://144.217.83.146/maps/"+id+".club");
        mapRepository.save(map);
    }

    public List<MapsDto> getAllMaps() {
        List<Map> maps = mapRepository.findAll();
        List<MapsDto> mapDTOs = new ArrayList<>();
        for (Map map : maps) {
            MapsDto threadDto = new MapsDto(
                map.getId(),
                map.getOwner().getUsername(),
                map.getOwner().getProfilePicture(),
                map.getName(),
                map.getDifficulty().toString(),
                map.getDownloadUrl(),
                map.getIsAdminVerified()
                );
                
            mapDTOs.add(threadDto);
        }
        return mapDTOs;
    }

    public Map saveMap(Map map) {
        return mapRepository.save(map);
    }
}