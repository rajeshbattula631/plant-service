package com.plant.portal.api.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.plant.portal.api.dto.request.AuthRequest;
import com.plant.portal.api.dto.request.UserRequest;
import com.plant.portal.api.dto.response.AuthResponse;
import com.plant.portal.api.dto.response.PlantResponse;
import com.plant.portal.api.dto.response.UserResponse;
import com.plant.portal.api.exception.BadRequestException;
import com.plant.portal.api.exception.ResourceNotFoundException;
import com.plant.portal.api.exception.UnauthorizedException;
import com.plant.portal.api.model.Plant;
import com.plant.portal.api.model.User;
import com.plant.portal.api.model.UserPrincipal;
import com.plant.portal.api.repository.PlantRepository;
import com.plant.portal.api.repository.UserRepository;
import com.plant.portal.api.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional 
public class UserServiceImpl implements UserDetailsService, UserService { 
 
    private final UserRepository userRepository; 
    private final PasswordEncoder passwordEncoder; 
    private final JwtTokenProvider tokenProvider; 
    private final ModelMapper modelMapper; 
    private final PlantRepository plantRepository; 
 
 
 
    // Register a new user 
    @Override 
    public UserResponse registerUser(UserRequest userRequest) { 
        if (userRepository.existsByUsername(userRequest.getUsername())) { 
            throw new BadRequestException("Username is already taken"); 
        } 
 
        if (userRepository.existsByEmail(userRequest.getEmail())) { 
            throw new BadRequestException("Email is already in use"); 
        } 
 
        User user = new User(); 
        user.setUsername(userRequest.getUsername()); 
        user.setEmail(userRequest.getEmail()); 
        user.setPasswordHash(passwordEncoder.encode(userRequest.getPassword())); 
        user.setRole(userRequest.getRole() != null ? userRequest.getRole() : "USER"); 
 
        User savedUser = userRepository.save(user); 
        return mapToResponse(savedUser); 
    } 
 
    // Authenticate user 
    @Override 
    public AuthResponse authenticateUser(AuthRequest authRequest) { 
        User user = userRepository.findByUsernameOrEmail(authRequest.getUsernameOrEmail(), authRequest.getUsernameOrEmail()) 
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials")); 
 
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash())) { 
            throw new UnauthorizedException("Invalid credentials"); 
        } 
 
        String token = tokenProvider.generateToken(UserPrincipal.create(user)); 
        return new AuthResponse(token, mapToResponse(user)); 
    } 
 
    // Get current user profile 
    @Override 
    public UserResponse getCurrentUser(UserPrincipal currentUser) { 
        User user = userRepository.findById(currentUser.getId()) 
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId())); 
        return mapToResponse(user); 
    } 
 
    // Update user profile 
@Override 
    public UserResponse updateUser(UserPrincipal currentUser, UserRequest userRequest) { 
        User user = userRepository.findById(currentUser.getId()) 
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId())); 
 
        if (!user.getUsername().equals(userRequest.getUsername())) { 
            if (userRepository.existsByUsername(userRequest.getUsername())) { 
                throw new BadRequestException("Username is already taken"); 
            } 
            user.setUsername(userRequest.getUsername()); 
        } 
 
        if (!user.getEmail().equals(userRequest.getEmail())) { 
            if (userRepository.existsByEmail(userRequest.getEmail())) { 
                throw new BadRequestException("Email is already in use"); 
            } 
            user.setEmail(userRequest.getEmail()); 
        } 
 
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) { 
            user.setPasswordHash(passwordEncoder.encode(userRequest.getPassword())); 
        } 
 
        if (userRequest.getRole() != null && currentUser.getRole().equals("ADMIN")) { 
            user.setRole(userRequest.getRole()); 
        } 
 
        User updatedUser = userRepository.save(user); 
        return mapToResponse(updatedUser); 
    } 
 
    // Delete user 
    @Override 
    public void deleteUser(UserPrincipal currentUser, UUID userId) { 
        if (!currentUser.getId().equals(userId) && !currentUser.getRole().equals("ADMIN")) { 
            throw new UnauthorizedException("You don't have permission to delete this user"); 
        } 
 
        User user = userRepository.findById(userId) 
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId)); 
 
        userRepository.delete(user); 
    } 
 
    // Get user by ID (admin only) 
    public UserResponse getUserById(UUID userId) { 
        User user = userRepository.findById(userId) 
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId)); 
        return mapToResponse(user); 
    } 
 
    // Get all users (admin only) 
    @Override 
    public List<UserResponse> getAllUsers() { 
        return userRepository.findAll().stream() 
                .map(this::mapToResponse) 
                .collect(Collectors.toList()); 
    } 
 
    // Add plant to favorites 
    public UserResponse addFavoritePlant(UserPrincipal currentUser, UUID plantId) { 
        User user = userRepository.findById(currentUser.getId()) 
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId())); 
 
        Plant plant = plantRepository.findById(plantId) 
                .orElseThrow(() -> new ResourceNotFoundException("Plant", "id", plantId)); 
 
        if (user.getFavoritePlants().contains(plant)) { 
            throw new BadRequestException("Plant is already in favorites"); 
        } 
 
        user.getFavoritePlants().add(plant); 
        User updatedUser = userRepository.save(user); 
        return mapToResponse(updatedUser); 
    } 
 
    // Remove plant from favorites 
    public UserResponse removeFavoritePlant(UserPrincipal currentUser, UUID plantId) { 
        User user = userRepository.findById(currentUser.getId()) 
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId())); 
 
        Plant plant = plantRepository.findById(plantId) 
                .orElseThrow(() -> new ResourceNotFoundException("Plant", "id", plantId)); 
 
        if (!user.getFavoritePlants().contains(plant)) { 
            throw new BadRequestException("Plant is not in favorites"); 
        } 
 
        user.getFavoritePlants().remove(plant); 
        User updatedUser = userRepository.save(user); 
        return mapToResponse(updatedUser); 
    } 
 
    // Get user's favorite plants 
    public List<PlantResponse> getUserFavoritePlants(UserPrincipal currentUser) { 
        User user = userRepository.findById(currentUser.getId()) 
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId())); 
 
 
 
        return user.getFavoritePlants().stream() 
                .map(plant -> modelMapper.map(plant, PlantResponse.class)) 
                .collect(Collectors.toList()); 
    } 
 
    // Helper method to map User to UserResponse 
    private UserResponse mapToResponse(User user) { 
        UserResponse response = modelMapper.map(user, UserResponse.class); 
        response.setFavoriteCount(user.getFavoritePlants().size()); 
        return response; 
    } 
 
    @Override 
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException { 
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail) 
                .orElseThrow(() -> 
                        new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail) 
                ); 
 
        return UserPrincipal.create(user); 
 
    } 
 
    public UserDetails loadUserById(UUID id) { 
        User user = userRepository.findById(id).orElseThrow( 
                () -> new ResourceNotFoundException("User", "id", id) 
        ); 
 
        return UserPrincipal.create(user); 
    }
}