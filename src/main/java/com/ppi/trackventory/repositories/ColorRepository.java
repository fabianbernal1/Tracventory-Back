package com.ppi.trackventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppi.trackventory.models.Color;

public interface ColorRepository extends JpaRepository<Color, Integer> {
}

