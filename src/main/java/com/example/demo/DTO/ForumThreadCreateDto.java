package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;

public class ForumThreadCreateDto {

    @NotBlank(message = "Owner ID is mandatory")
    private Integer ownerId;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Value is mandatory")
    private String value;

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
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
}
