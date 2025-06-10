package com.plant.portal.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "pp_plants") 
@Getter
@Setter
@NoArgsConstructor 
public class Plant { 


    @Id 
    @GeneratedValue 
    @UuidGenerator(style = UuidGenerator.Style.TIME) 
    private UUID id;
 
    @Column(nullable = false) 
    private String name; 
 
    private String scientificName; 
    private String description; 
 
    @Column(nullable = false) 
    private BigDecimal price; 
 
    @Column(nullable = false) 
    private Integer stockQuantity; 
 
    private String careDifficulty; 
    private Integer waterFrequencyDays; 
    private String sunlightRequirements; 
    private String imageUrl; 
 
    @CreationTimestamp 
    private LocalDateTime createdAt; 
 
    @UpdateTimestamp 
    private LocalDateTime updatedAt; 
 
    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<PlantHealth> healthMetrics = new ArrayList<>(); 
 
    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<OrderItem> orderItems = new ArrayList<>(); 
 
    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<ShoppingCart> inCarts = new ArrayList<>(); 
 
    @ManyToMany 
    @JoinTable( 
        name = "pp_plant_categories", 
        joinColumns = @JoinColumn(name = "plant_id"), 
        inverseJoinColumns = @JoinColumn(name = "category_id")) 
    private Set<Category> categories = new HashSet<>(); 
 
    @ManyToMany(mappedBy = "favoritePlants") 
    private Set<User> favoritedByUsers = new HashSet<>(); 
} 
