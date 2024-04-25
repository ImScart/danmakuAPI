package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MapCommentsDto {

    private Long mapId;

    public MapCommentsDto(@JsonProperty("mapId") Long mapId) {
        this.mapId = mapId;
    }

    public Long getMapId() {
        return mapId;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }
}
