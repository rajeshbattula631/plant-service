package com.plant.portal.api.model;

import org.springframework.security.core.GrantedAuthority; 
import org.springframework.security.core.authority.SimpleGrantedAuthority; 
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection; 
import java.util.Collections; 
import java.util.List; 
import java.util.UUID; 
 
@Getter
@AllArgsConstructor
@ToString
public class UserPrincipal implements UserDetails { 
 
    private final UUID id; 
    private final String username; 
    private final String email; 
    private final String passwordHash; 
    private final Collection<? extends GrantedAuthority> authorities; 
 
    
 
    public static UserPrincipal create(User user) { 
        List<GrantedAuthority> authorities = Collections.singletonList( 
                new SimpleGrantedAuthority(user.getRole())); 
 
        return new UserPrincipal( 
                user.getId(), 
                user.getUsername(), 
                user.getEmail(), 
                user.getPasswordHash(), 
                authorities 
        ); 
    } 
 
    @Override 
    public Collection<? extends GrantedAuthority> getAuthorities() { 
        return authorities; 
    } 
 
    @Override 
    public String getPassword() { 
        return passwordHash; 
    } 
 
    @Override 
    public String getUsername() { 
        return username; 
    } 
 
    @Override 
    public boolean isAccountNonExpired() { 
        return true; 
    } 
 
    @Override 
    public boolean isAccountNonLocked() { 
        return true; 
    } 
 
    @Override 
    public boolean isCredentialsNonExpired() { 
        return true; 
    } 
 
    @Override 
    public boolean isEnabled() { 
        return true; 
    } 
 
    public String getRole() { 
        return authorities.stream() 
                .map(GrantedAuthority::getAuthority) 
                .findFirst() 
                .orElse("USER"); 
    } 
 
    public UUID getId() { 
        return id; 
    } 
 
    public String getEmail() { 
        return email; 
    } 
 
    public String getPasswordHash() { 
        return passwordHash; 
    } 
} 
