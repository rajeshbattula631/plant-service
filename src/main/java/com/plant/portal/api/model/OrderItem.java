package com.plant.portal.api.model;

import java.math.BigDecimal;
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
@Table(name = "pp_order_items")
@Getter 
@Setter 
@NoArgsConstructor
public class OrderItem {
    @Id 
    @GeneratedValue 
    @UuidGenerator(style = UuidGenerator.Style.TIME) 
    private UUID id; 
 
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "order_id", nullable = false) 
    private Order order; 
 
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "plant_id", nullable = false) 
    private Plant plant; 
 
    @Column(nullable = false) 
    private Integer quantity; 
 
    @Column(nullable = false) 
    private BigDecimal unitPrice; 

}
