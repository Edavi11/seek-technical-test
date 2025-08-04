package com.seek.test.seek_test.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private String testSecret;
    private long testExpiration;
    private String testUsername;

    @BeforeEach
    void setUp() {
        testSecret = "1e32ef1581f5954f9e478afa401c7bd1631b08e94a4721c3fefea4d351a6217e";
        testExpiration = 86400000L; // 24 hours
        testUsername = "testuser";

        // Set private fields using ReflectionTestUtils with correct field names
        ReflectionTestUtils.setField(jwtService, "secretKey", testSecret);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", testExpiration);
    }

    @Test
    void extractUsername_Success() {
        // Given
        String token = jwtService.generateToken(testUsername);

        // When
        String extractedUsername = jwtService.extractUsername(token);

        // Then
        assertEquals(testUsername, extractedUsername);
    }

    @Test
    void extractUsername_InvalidToken() {
        // Given
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.invalid.signature";

        // When & Then
        assertThrows(Exception.class, () -> {
            jwtService.extractUsername(invalidToken);
        });
    }

    @Test
    void extractClaim_Success() {
        // Given
        String token = jwtService.generateToken(testUsername);

        // When
        String extractedUsername = jwtService.extractClaim(token, Claims::getSubject);

        // Then
        assertEquals(testUsername, extractedUsername);
    }

    @Test
    void generateToken_Success() {
        // When
        String token = jwtService.generateToken(testUsername);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        
        // Verify token structure (should have 3 parts separated by dots)
        String[] parts = token.split("\\.");
        assertEquals(3, parts.length);
    }

    @Test
    void generateToken_WithExtraClaims() {
        // Given
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "ADMIN");
        extraClaims.put("department", "IT");

        // When
        String token = jwtService.generateToken(extraClaims, testUsername);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        
        // Verify token can be parsed
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(testUsername, extractedUsername);
    }

    @Test
    void isTokenValid_ValidToken() {
        // Given
        String token = jwtService.generateToken(testUsername);

        // When
        boolean isValid = jwtService.isTokenValid(token, testUsername);

        // Then
        assertTrue(isValid);
    }

    @Test
    void isTokenValid_InvalidUsername() {
        // Given
        String token = jwtService.generateToken(testUsername);
        String wrongUsername = "wronguser";

        // When
        boolean isValid = jwtService.isTokenValid(token, wrongUsername);

        // Then
        assertFalse(isValid);
    }

    @Test
    void isTokenValid_ExpiredToken() {
        // Given
        // Set a very short expiration time
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1L);
        String token = jwtService.generateToken(testUsername);

        // Wait for token to expire
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // When
        boolean isValid = jwtService.isTokenValid(token, testUsername);

        // Then
        assertFalse(isValid);
    }

    @Test
    void isTokenValid_InvalidToken() {
        // Given
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.invalid.signature";

        // When
        boolean isValid = jwtService.isTokenValid(invalidToken, testUsername);

        // Then
        assertFalse(isValid);
    }

    @Test
    void generateToken_WithNullUsername() {
        // When & Then
        // Note: JwtService doesn't validate null username, so this should not throw
        String token = jwtService.generateToken(null);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void generateToken_WithEmptyUsername() {
        // When & Then
        // Note: JwtService doesn't validate empty username, so this should not throw
        String token = jwtService.generateToken("");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
} 