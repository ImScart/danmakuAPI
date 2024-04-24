package com.example.demo.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Tables.Map;

public interface MapRepository extends JpaRepository<Map, Long> {
    Optional<Map> findById(Integer id);
}