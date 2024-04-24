package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ForumThreadLikesDto {

    private Long forumThreadId;

    public ForumThreadLikesDto(@JsonProperty("forumThreadId") Long forumThreadId) {
        this.forumThreadId = forumThreadId;
    }

    public Long getForumThreadId() {
        return forumThreadId;
    }

    public void setForumThreadId(Long forumThreadId) {
        this.forumThreadId = forumThreadId;
    }
}
