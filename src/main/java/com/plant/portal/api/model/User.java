package com.plant.portal.api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pp_users")
@ToString
public class User {

    @Id 
    @GeneratedValue 
    @UuidGenerator(style = UuidGenerator.Style.TIME) 
    private UUID id; 
 
    @CreationTimestamp 
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "created", nullable = true) 
    private LocalDateTime created; 
 
    @UpdateTimestamp 
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "updated", nullable = true) 
    private LocalDateTime updated; 
 
    @Column(nullable = false, unique = true) 
    private String username; 
 
    @Column(nullable = false, unique = true) 
    private String email; 
 
    @Column(nullable = false) 
    private String passwordHash; 
 
    private String role = "USER"; 
 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<Order> orders = new ArrayList<>(); 
 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<ShoppingCart> cartItems = new ArrayList<>(); 
 
    @ManyToMany 
    @JoinTable( 
        name = "user_favorites", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "plant_id")) 
    private Set<Plant> favoritePlants = new HashSet<>(); 

}