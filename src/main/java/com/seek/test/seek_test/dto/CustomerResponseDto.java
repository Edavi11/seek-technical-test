package com.seek.test.seek_test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private Integer age;
    private LocalDate birthDate;
    private LocalDate estimatedLifeExpectancy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 