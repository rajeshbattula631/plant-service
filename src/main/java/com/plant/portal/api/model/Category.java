package com.plant.portal.api.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "pp_categories")
@Getter 
@Setter 
@NoArgsConstructor
public class Category {
    @Id 
    @GeneratedValue 
    @UuidGenerator(style = UuidGenerator.Style.TIME) 
    private UUID id; 
 
    @Column(nullable = false) 
    private String name; 
 
    private String description; 
 
    @ManyToMany(mappedBy = "categories") 
    private Set<Plant> plants = new HashSet<>();
}
