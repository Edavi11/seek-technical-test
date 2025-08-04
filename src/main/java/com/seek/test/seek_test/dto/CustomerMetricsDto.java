package com.seek.test.seek_test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMetricsDto {

    private Long totalCustomers;
    private Double averageAge;
    private Double standardDeviation;
    private Integer minAge;
    private Integer maxAge;
    private Double medianAge;
} 