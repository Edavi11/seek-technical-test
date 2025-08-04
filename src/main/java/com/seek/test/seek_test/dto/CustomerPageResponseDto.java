package com.seek.test.seek_test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Paginated customer response")
public class CustomerPageResponseDto {
    
    @Schema(description = "List of customers in the current page", example = "[]")
    private List<CustomerResponseDto> content;
    
    @Schema(description = "Current page number (1-based)", example = "1")
    private int pageNumber;
    
    @Schema(description = "Number of elements per page", example = "10")
    private int pageSize;
    
    @Schema(description = "Total number of elements", example = "25")
    private long totalElements;
    
    @Schema(description = "Total number of pages", example = "3")
    private int totalPages;
    
    @Schema(description = "Whether this is the first page", example = "true")
    private boolean first;
    
    @Schema(description = "Whether this is the last page", example = "false")
    private boolean last;
    
    @Schema(description = "Whether there is a next page", example = "true")
    @JsonProperty("hasNext")
    private boolean hasNext;
    
    @Schema(description = "Whether there is a previous page", example = "false")
    @JsonProperty("hasPrevious")
    private boolean hasPrevious;
} 