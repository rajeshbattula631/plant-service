package com.plant.portal.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "pp_orders") 
@Getter 
@Setter 
@NoArgsConstructor
public class Order {
    

    @Id 
    @GeneratedValue 
    @UuidGenerator(style = UuidGenerator.Style.TIME) 
    private UUID id; 

 
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "user_id", nullable = false) 
    private User user; 
 
    @CreationTimestamp 
    private LocalDateTime orderDate; 
 
    @Column(nullable = false) 
    private BigDecimal totalAmount; 
 
    private String status = "PENDING"; 
 
    @Column(nullable = false) 
    private String shippingAddress; 
 
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<OrderItem> items = new ArrayList<>(); 

}
