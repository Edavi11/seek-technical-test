package com.seek.test.seek_test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User creation response")
public class UserCreateResponseDto {
    
    @Schema(description = "Success message", example = "User created successfully")
    private String message;
    
    @Schema(description = "Username of the created user", example = "john")
    private String username;
    
    @Schema(description = "User ID", example = "2")
    private Long userId;
    
    @Schema(description = "Timestamp when the user was created", example = "2025-01-03T13:31:23.537")
    private LocalDateTime createdAt;
    
    @Schema(description = "Operation status", example = "SUCCESS")
    private String status;
} 