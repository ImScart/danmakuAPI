package com.example.demo.DTO;

public class CreateMapCommentDto {
    private Long ownerId;
    private String ownerName;
    private String ownerProfilePicture;
    private Long mapId;
    private String value;

    public CreateMapCommentDto(Long ownerId,String ownerName, String ownerProfilePicture, Long mapId, String value) {
        this.ownerId=ownerId;
        this.ownerName=ownerName;
        this.ownerProfilePicture=ownerProfilePicture;
        this.mapId = mapId;
        this.value = value;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerProfilePicture() {
        return ownerProfilePicture;
    }

    public void setOwnerProfilePicture(String ownerProfilePicture) {
        this.ownerProfilePicture = ownerProfilePicture;
    }

    public Long getMapId() {
        return mapId;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}