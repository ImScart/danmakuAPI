package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ForumThreadCommentsDto {

    private Long forumThreadId;

    public ForumThreadCommentsDto(@JsonProperty("forumThreadId") Long forumThreadId) {
        this.forumThreadId = forumThreadId;
    }

    public Long getForumThreadId() {
        return forumThreadId;
    }

    public void setForumThreadId(Long forumThreadId) {
        this.forumThreadId = forumThreadId;
    }
}
