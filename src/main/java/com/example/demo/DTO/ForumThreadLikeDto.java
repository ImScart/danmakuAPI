package com.example.demo.DTO;

public class ForumThreadLikeDto {

    private Long id;
    private Long ownerId;
    private Long forumThreadId;

    public ForumThreadLikeDto(Long id, Long ownerId, Long forumThreadId) {
        this.id = id;
        this.ownerId = ownerId;
        this.forumThreadId = forumThreadId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getForumThreadId() {
        return forumThreadId;
    }

    public void setForumThreadId(Long forumThreadId) {
        this.forumThreadId = forumThreadId;
    }
}
