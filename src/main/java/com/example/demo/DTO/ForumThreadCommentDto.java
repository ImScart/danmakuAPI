package com.example.demo.DTO;

public class ForumThreadCommentDto {
    private Long ownerId;
    private String ownerName;
    private String ownerProfilePicture;
    private Long threadId;
    private String value;

    public ForumThreadCommentDto(Long ownerId,String ownerName, String ownerProfilePicture, Long threadId, String value) {
        this.ownerId=ownerId;
        this.ownerName=ownerName;
        this.ownerProfilePicture=ownerProfilePicture;
        this.threadId = threadId;
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

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}