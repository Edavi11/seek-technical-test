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
@Schema(description = "Delete operation response")
public class DeleteResponseDto {
    
    @Schema(description = "Success message", example = "Customer deleted successfully")
    private String message;
    
    @Schema(description = "ID of the deleted customer", example = "1")
    private Long customerId;
    
    @Schema(description = "Timestamp when the deletion occurred", example = "2025-01-03T13:31:23.537")
    private LocalDateTime deletedAt;
    
    @Schema(description = "Operation status", example = "SUCCESS")
    private String status;
} 