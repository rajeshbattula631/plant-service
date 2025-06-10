package com.plant.portal.api.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.plant.portal.api.model.User;

import java.util.Optional; 
import java.util.UUID; 
 
@Repository 
public interface UserRepository extends JpaRepository<User, UUID> { 
 
 
    Boolean existsByUsername(String username); 
    Boolean existsByEmail(String email); 
 
    Optional<User> findByUsername(String username); 
    Optional<User> findByEmail(String email); 
    Optional<User> findByUsernameOrEmail(String username, String email); 
 
} 
