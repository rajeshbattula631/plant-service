package com.plant.portal.api.service;

import java.util.List;

import com.plant.portal.api.dto.request.PlantRequest;
import com.plant.portal.api.dto.response.PlantHealthResponse;
import com.plant.portal.api.dto.response.PlantResponse;

public interface PlantService {
    List<PlantResponse> getAllPlants(); 
 
    PlantResponse getPlantById(Long id); 
 
    PlantResponse updatePlant(Long id, PlantRequest plantRequest); 
 
    void deletePlant(Long id); 
 
    PlantHealthResponse getPlantHealth(Long id); 
 
    PlantHealthResponse checkPlantHealth(Long id); 
 
    PlantResponse createPlant(PlantRequest plantRequest); 

}
