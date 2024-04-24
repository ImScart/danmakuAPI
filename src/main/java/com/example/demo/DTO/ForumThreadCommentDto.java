package com.example.demo.DTO;

public class ForumThreadCommentDto {

    private Long ownerId;
    private Long threadId;
    private String value;

    public ForumThreadCommentDto(Long ownerId, Long threadId, String value) {
        this.ownerId = ownerId;
        this.threadId = threadId;
        this.value = value;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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