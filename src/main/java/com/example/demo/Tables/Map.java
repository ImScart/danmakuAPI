package com.example.demo.Tables;

import jakarta.persistence.*;

@Entity
@Table(name = "Map")
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ownerID", nullable = false)
    private UserAccount owner;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private Difficulty difficulty;

    @Column(name = "downloadUrl", nullable = false, length = 255)
    private String downloadUrl;

    @Column(name = "isAdminVerified", nullable = false)
    private Boolean isAdminVerified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getOwner() {
        return owner;
    }

    public void setOwner(UserAccount owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
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

    public void setIsAdminVerified(Boolean adminVerified) {
        isAdminVerified = adminVerified;
    }

    public enum Difficulty {
        easy, medium, hard, lunatic
    }
}
