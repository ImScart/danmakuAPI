package com.example.demo.DTO;

public class MapsDto {
    private Integer id;
    private String ownerName;
    private String ownerProfilePicture;
    private String name;
    private String difficulty;
    private String downloadUrl;
    private Boolean isAdminVerified;


    public MapsDto() {
    }

    public MapsDto(Integer id, String ownerName, String ownerProfilePicture, String name, String difficulty, String downloadUrl, Boolean isAdminVerified) {
        this.id = id;
        this.ownerName = ownerName;
        this.ownerProfilePicture=ownerProfilePicture;
        this.name = name;
        this.difficulty = difficulty;
        this.downloadUrl = downloadUrl;
        this.isAdminVerified = isAdminVerified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.name = ownerName;
    }

    public String getOwnerProfilePicture()
    {
        return ownerProfilePicture;
    }

    public void setOwnerProfilePicture(String ownerProfilePicrure)
    {
        this.ownerProfilePicture=ownerProfilePicrure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Boolean getIsAdminVerified() {
        return isAdminVerified;
    }

    public void setIsAdminVerified(Boolean isAdminVerified) {
        this.isAdminVerified = isAdminVerified;
    }
}
