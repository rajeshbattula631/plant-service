package com.plant.portal.api.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.web.bind.annotation.*;

import com.plant.portal.api.dto.request.AuthRequest;
import com.plant.portal.api.dto.request.UserRequest;
import com.plant.portal.api.dto.response.AuthResponse;
import com.plant.portal.api.dto.response.UserResponse;
import com.plant.portal.api.model.UserPrincipal;
import com.plant.portal.api.service.UserService;
import com.plant.portal.api.util.CurrentUser;

import java.util.List; 
import java.util.UUID; 
 
@RestController 
@RequestMapping("/api/users") 
@AllArgsConstructor
public class UserController { 
 
 
    private final UserService userService; 
 
    
 
    @PostMapping("/register") 
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) { 
        return new ResponseEntity<>(userService.registerUser(userRequest), HttpStatus.CREATED); 
    } 
 
    // Authenticate user 
    @PostMapping("/authenticate") 
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody AuthRequest authRequest) { 
        return ResponseEntity.ok(userService.authenticateUser(authRequest)); 
    } 
 
    // Get current user profile 
    @GetMapping("/me") 
    public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser UserPrincipal currentUser) { 
        return ResponseEntity.ok(userService.getCurrentUser(currentUser)); 
    } 
 
    // Update user profile 
    @PutMapping("/me") 
    public ResponseEntity<UserResponse> updateUser( 
            @CurrentUser UserPrincipal currentUser, 
            @Valid @RequestBody UserRequest userRequest) { 
        return ResponseEntity.ok(userService.updateUser(currentUser, userRequest)); 
    } 
 
    // Delete user account 
    @DeleteMapping("/me") 
    public ResponseEntity<Void> deleteUser(@CurrentUser UserPrincipal currentUser) { 
        userService.deleteUser(currentUser, currentUser.getId()); 
        return ResponseEntity.noContent().build(); 
    } 
 
    // Admin endpoints 
    @GetMapping 
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<List<UserResponse>> getAllUsers() { 
        return ResponseEntity.ok(userService.getAllUsers()); 
    } 
 
    @GetMapping("/{userId}") 
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<UserDetails> getUserById(@PathVariable String userId) { 
        return ResponseEntity.ok(userService.loadUserById(UUID.fromString(userId))); 
    } 
 
    @DeleteMapping("/{userId}") 
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<Void> deleteUser(@CurrentUser UserPrincipal currentUser, @PathVariable String userId) { 
        userService.deleteUser(currentUser, UUID.fromString(userId)); 
        return ResponseEntity.noContent().build(); 
    } 
 
    // Favorite plants endpoints 
//    @GetMapping("/me/favorites") 
//    public ResponseEntity<List<PlantResponse>> getUserFavoritePlants(@CurrentUser UserPrincipal currentUser) { 
//        return ResponseEntity.ok(userService.getUserFavoritePlants(currentUser)); 
//    } 
// 
//    @PostMapping("/me/favorites/{plantId}") 
//    public ResponseEntity<UserResponse> addFavoritePlant( 
//            @CurrentUser UserPrincipal currentUser, 
//            @PathVariable Long plantId) { 
//        return ResponseEntity.ok(userService.addFavoritePlant(currentUser, plantId)); 
//    } 
// 
//    @DeleteMapping("/me/favorites/{plantId}") 
//    public ResponseEntity<UserResponse> removeFavoritePlant( 
//            @CurrentUser UserPrincipal currentUser, 
//            @PathVariable Long plantId) { 
//        return ResponseEntity.ok(userService.removeFavoritePlant(currentUser, plantId)); 
//    } 
 
} 
