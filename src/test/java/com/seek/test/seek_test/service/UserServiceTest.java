package com.seek.test.seek_test.service;

import com.seek.test.seek_test.dto.UserCreateResponseDto;
import com.seek.test.seek_test.entity.User;
import com.seek.test.seek_test.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private String testUsername;
    private String testPassword;
    private String encodedPassword;

    @BeforeEach
    void setUp() {
        testUsername = "john";
        testPassword = "password123";
        encodedPassword = "$2a$10$encoded.password.hash";
        
        testUser = new User();
        testUser.setId(2L);
        testUser.setUsername(testUsername);
        testUser.setPassword(encodedPassword);
        testUser.setIsActive(true);
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createUser_Success() {
        // Given
        when(userRepository.existsByUsername(testUsername)).thenReturn(false);
        when(passwordEncoder.encode(testPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        UserCreateResponseDto result = userService.createUser(testUsername, testPassword);

        // Then
        assertNotNull(result);
        assertEquals("User 'john' created successfully", result.getMessage());
        assertEquals(testUsername, result.getUsername());
        assertEquals(testUser.getId(), result.getUserId());
        assertEquals(testUser.getCreatedAt(), result.getCreatedAt());
        assertEquals("SUCCESS", result.getStatus());
        
        verify(userRepository, times(1)).existsByUsername(testUsername);
        verify(passwordEncoder, times(1)).encode(testPassword);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_UsernameAlreadyExists() {
        // Given
        when(userRepository.existsByUsername(testUsername)).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(testUsername, testPassword);
        });

        assertEquals("Username already exists: " + testUsername, exception.getMessage());
        verify(userRepository, times(1)).existsByUsername(testUsername);
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }


} 