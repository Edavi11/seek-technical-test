package com.seek.test.seek_test.service;

import com.seek.test.seek_test.dto.UserCreateResponseDto;
import com.seek.test.seek_test.dto.UserResponseDto;
import com.seek.test.seek_test.dto.UserActionResponseDto;
import com.seek.test.seek_test.dto.PasswordChangeRequestDto;
import com.seek.test.seek_test.entity.User;
import com.seek.test.seek_test.exception.PasswordValidationException;
import com.seek.test.seek_test.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create a new user
     * @param username the username
     * @param password the plain text password
     * @return the creation response with safe information
     */
    public UserCreateResponseDto createUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
        
        User user = new User(username, passwordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        
        return UserCreateResponseDto.builder()
                .message("User '" + username + "' created successfully")
                .username(savedUser.getUsername())
                .userId(savedUser.getId())
                .createdAt(savedUser.getCreatedAt())
                .status("SUCCESS")
                .build();
    }

    /**
     * Find user by ID
     * @param id the user ID to search for
     * @return Optional containing the user if found
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }



    /**
     * Find all users (active and inactive)
     * @return list of all users with safe information
     */
    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /**
     * Find all active users only
     * @return list of active users with safe information
     */
    public List<UserResponseDto> findAllActiveUsers() {
        return userRepository.findAll().stream()
                .filter(User::getIsActive)
                .map(this::mapToResponseDto)
                .toList();
    }

    /**
     * Map User entity to UserResponseDto
     */
    private UserResponseDto mapToResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    /**
     * Update user password with validation
     * @param id the user ID
     * @param request the password change request
     * @return the action response
     */
    public UserActionResponseDto updatePassword(Long id, PasswordChangeRequestDto request) {
        // Validate request
        if (!request.isPasswordConfirmationValid()) {
            throw new PasswordValidationException("New password and confirmation do not match");
        }
        
        if (!request.isNewPasswordDifferent()) {
            throw new PasswordValidationException("New password must be different from current password");
        }
        
        // Find user
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        
        // Validate current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new PasswordValidationException("Current password is incorrect");
        }
        
        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        User savedUser = userRepository.save(user);
        
        return UserActionResponseDto.builder()
                .message("Password updated successfully for user '" + savedUser.getUsername() + "'")
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .action("PASSWORD_UPDATED")
                .performedAt(LocalDateTime.now())
                .status("SUCCESS")
                .build();
    }

    /**
     * Deactivate user
     * @param id the user ID to deactivate
     * @return the action response
     */
    public UserActionResponseDto deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        
        user.setIsActive(false);
        User savedUser = userRepository.save(user);
        
        return UserActionResponseDto.builder()
                .message("User '" + savedUser.getUsername() + "' deactivated successfully")
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .action("DEACTIVATED")
                .performedAt(LocalDateTime.now())
                .status("SUCCESS")
                .build();
    }

    /**
     * Activate user
     * @param id the user ID to activate
     * @return the action response
     */
    public UserActionResponseDto activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        
        user.setIsActive(true);
        User savedUser = userRepository.save(user);
        
        return UserActionResponseDto.builder()
                .message("User '" + savedUser.getUsername() + "' activated successfully")
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .action("ACTIVATED")
                .performedAt(LocalDateTime.now())
                .status("SUCCESS")
                .build();
    }

    /**
     * Delete user completely
     * @param id the user ID to delete
     * @return the action response
     */
    public UserActionResponseDto deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        
        String username = user.getUsername();
        userRepository.delete(user);
        
        return UserActionResponseDto.builder()
                .message("User '" + username + "' deleted successfully")
                .userId(id)
                .username(username)
                .action("DELETED")
                .performedAt(LocalDateTime.now())
                .status("SUCCESS")
                .build();
    }
} 