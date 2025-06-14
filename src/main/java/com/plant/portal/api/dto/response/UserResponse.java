package com.plant.portal.api.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class UserResponse { 
    private Long id; 
    private String username; 
    private String email; 
    private String role; 
    private LocalDateTime createdAt; 
    private int favoriteCount; 
}
