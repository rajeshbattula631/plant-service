package com.plant.portal.api.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "pp_shopping_cart") 
@Getter 
@Setter 
@NoArgsConstructor 
public class ShoppingCart { 
    
    @Id 
    @GeneratedValue 
    private UUID id; 
 
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "user_id", nullable = false) 
    private User user; 
 
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "plant_id", nullable = false) 
    private Plant plant; 
 
    @Column(nullable = false) 
    private Integer quantity = 1; 
 
    @CreationTimestamp 
    private LocalDateTime addedAt; 
} 
