package com.plant.portal.api.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.plant.portal.api.model.Plant;

import java.util.UUID; 
 
@Repository 
public interface PlantRepository extends JpaRepository<Plant, UUID> { 
} 
