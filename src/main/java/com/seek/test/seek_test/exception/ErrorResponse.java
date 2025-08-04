package com.seek.test.seek_test.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error response structure")
public class ErrorResponse {
    
    @Schema(description = "Timestamp when the error occurred", example = "2025-01-03T13:31:23.537")
    private LocalDateTime timestamp;
    
    @Schema(description = "HTTP status code", example = "404")
    private int status;
    
    @Schema(description = "Error type", example = "Not Found")
    private String error;
    
    @Schema(description = "Error message", example = "Customer not found with ID: 40")
    private String message;
    
    @Schema(description = "Request path", example = "/api/v1/customers/40")
    private String path;
    
    @Schema(description = "Additional error details", example = "null")
    private Map<String, String> details;
} 