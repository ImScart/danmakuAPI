package com.example.demo.DTO;

import org.springframework.web.multipart.MultipartFile;

public class CreateMapDto {
    private Integer ownerID;
    private MultipartFile file;
    private String name;
    private String difficulty;

    public CreateMapDto(Integer ownerID, MultipartFile file,String difficulty) {
        this.ownerID = ownerID;
        this.file=file;
        this.difficulty = difficulty;
    }

    public Integer getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Integer ownerID) {
        this.ownerID = ownerID;
    }

    public MultipartFile getFile()
    {
        return file;
    }

    public void setFile(MultipartFile file)
    {
        this.file=file;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
