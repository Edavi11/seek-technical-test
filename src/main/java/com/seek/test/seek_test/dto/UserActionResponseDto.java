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
@Schema(description = "User action response")
public class UserActionResponseDto {
    
    @Schema(description = "Success message", example = "User activated successfully")
    private String message;
    
    @Schema(description = "User ID", example = "1")
    private Long userId;
    
    @Schema(description = "Username", example = "admin")
    private String username;
    
    @Schema(description = "Action performed", example = "ACTIVATED")
    private String action;
    
    @Schema(description = "Timestamp when the action was performed", example = "2025-01-03T13:31:23.537")
    private LocalDateTime performedAt;
    
    @Schema(description = "Operation status", example = "SUCCESS")
    private String status;
} 