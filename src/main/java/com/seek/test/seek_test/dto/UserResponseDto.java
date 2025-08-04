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
@Schema(description = "User response (safe information only)")
public class UserResponseDto {
    
    @Schema(description = "User ID", example = "1")
    private Long id;
    
    @Schema(description = "Username", example = "admin")
    private String username;
    
    @Schema(description = "Whether the user is active", example = "true")
    private Boolean isActive;
    
    @Schema(description = "When the user was created", example = "2025-01-03T13:31:23.537")
    private LocalDateTime createdAt;
    
    @Schema(description = "When the user was last updated", example = "2025-01-03T13:31:23.537")
    private LocalDateTime updatedAt;
} 