package com.finvision.core.service;

import com.finvision.core.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    
    User updateUser(Long id, User user);
    
    void deleteUser(Long id);
    
    Optional<User> getUser(Long id);
    
    Optional<User> getUserByUsername(String username);
    
    Optional<User> getUserByEmail(String email);
    
    List<User> getAllUsers();
    
    void changePassword(Long userId, String newPassword);
    
    void enableUser(Long userId);
    
    void disableUser(Long userId);
    
    boolean isUsernameUnique(String username);
    
    boolean isEmailUnique(String email);
} 