package com.example.demo.DTO;

import java.time.LocalDateTime;

public class ForumThreadDto {

    private Long id;
    private String title;
    private String value;
    private LocalDateTime created;
    private Long ownerId;
    private String username;
    private String profilePicture;

    public ForumThreadDto(Long id, String title, String value, LocalDateTime created, Long ownerId, String username,
            String profilePicture) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.created = created;
        this.ownerId = ownerId;
        this.username = username;
        this.profilePicture = profilePicture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
