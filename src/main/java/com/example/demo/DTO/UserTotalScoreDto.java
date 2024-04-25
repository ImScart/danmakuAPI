package com.example.demo.DTO;

public class UserTotalScoreDto {
    private Long ownerId;
    private String username;
    private Long totalScore;

    public UserTotalScoreDto(Long ownerId, String username, Long totalScore) {
        this.ownerId = ownerId;
        this.username = username;
        this.totalScore = totalScore;
    }


    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerID(Long ownerID) {
        this.ownerId = ownerID;
    }

    public String getUsername()
    {
        return username;
    }

    public void serUsername(String username)
    {
        this.username=username;
    }

    public Long getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Long totalScore) {
        this.totalScore = totalScore;
    }
}
