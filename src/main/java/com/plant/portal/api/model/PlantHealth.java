package com.plant.portal.api.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "pp_plant_health") 
@Getter 
@Setter 
@NoArgsConstructor
public class PlantHealth {
    
    @Id 
    @GeneratedValue 
    @UuidGenerator(style = UuidGenerator.Style.TIME) 
    private UUID id; 
 
    @Column(nullable = false, name = "health_status") 
    private String healthStatus; 
 
    @Column(nullable = false, name = "notes") 
    private String notes; 
 
    @Column(nullable = false, name = "soil_moisture") 
    private Double soilMoisture; 
 
    @Column(nullable = false, name = "light_exposure") 
    private Double lightExposure; 
 
    @Column(nullable = false, name = "last_checked", updatable = false) 
    private LocalDateTime lastChecked; 
 
    @Column(nullable = false, name = "temperature") 
    private Double temperature; 
 
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "plant_id", nullable = false) 
    private Plant plant; 

    public static final String EXCELLENT = "excellent"; 
    public static final String GOOD = "good"; 
    public static final String FAIR = "fair"; 
    public static final String POOR = "poor"; 
 
    public boolean needsAttention(){ 
       return POOR.equals(healthStatus) || FAIR.equals(healthStatus); 
    } 
 
    public String getHealthStatusDescription(){ 
        return switch (healthStatus) { 
            case EXCELLENT -> "Plant is in perfect condition"; 
            case GOOD -> "Plant is healthy"; 
            case FAIR -> "Plant needs some care"; 
            case POOR -> "Plant needs immediate attention"; 
            default -> "Health status unknown"; 
        }; 
    } 

}
