package com.plant.portal.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
public class ErrorResponse { 
    private int status; 
    private String message; 
    private long timestamp; 
}