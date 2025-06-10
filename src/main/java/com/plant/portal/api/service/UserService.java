package com.plant.portal.api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

import com.plant.portal.api.dto.request.AuthRequest;
import com.plant.portal.api.dto.request.UserRequest;
import com.plant.portal.api.dto.response.AuthResponse;
import com.plant.portal.api.dto.response.UserResponse;
import com.plant.portal.api.model.UserPrincipal;

import jakarta.validation.Valid;

public interface UserService {
    public UserResponse registerUser(UserRequest userRequest); 
    public AuthResponse authenticateUser(AuthRequest authRequest); 
    public UserResponse getCurrentUser(UserPrincipal currentUser); 
    public UserDetails loadUserById(UUID id); 
 
    UserResponse updateUser(UserPrincipal currentUser, @Valid UserRequest userRequest); 
 
    void deleteUser(UserPrincipal currentUser, UUID id); 
 
    List<UserResponse> getAllUsers();
}
