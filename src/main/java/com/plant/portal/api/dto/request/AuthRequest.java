package com.plant.portal.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class AuthRequest { 
    @NotBlank(message = "Username or email is required") 
    private String usernameOrEmail; 
 
    @NotBlank(message = "Password is required") 
    private String password; 
}
