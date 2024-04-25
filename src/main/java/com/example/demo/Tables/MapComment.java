package com.example.demo.Tables;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "MapComment")
public class MapComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ownerID", nullable = false)
    private UserAccount owner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mapID", nullable = false)
    private Map map;

    @Column(name = "created", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime created;

    @Column(name = "value", nullable = false, columnDefinition = "TEXT")
    private String value;


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

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
