package com.seek.test.seek_test.entity;

import com.seek.test.seek_test.exception.AgeBirthDateMismatchException;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be greater than or equal to 0")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isDeleted == null) {
            isDeleted = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Calculates age based on birth date
     */
    public void calculateAge() {
        if (birthDate != null) {
            this.age = Period.between(birthDate, LocalDate.now()).getYears();
        }
    }

    /**
     * Validates that the provided age matches the calculated age from birth date
     * @throws AgeBirthDateMismatchException if the ages don't match
     */
    public void validateAgeMatchesBirthDate() {
        if (birthDate != null && age != null) {
            int calculatedAge = Period.between(birthDate, LocalDate.now()).getYears();
            
            if (age != calculatedAge) {
                throw new AgeBirthDateMismatchException(
                    String.format("Age mismatch: provided age is %d, but calculated age from birth date %s is %d", 
                        age, birthDate, calculatedAge)
                );
            }
        }
    }

    /**
     * Calculates estimated life expectancy based on current age
     * Using global average life expectancy data
     */
    public LocalDate calculateEstimatedLifeExpectancy() {
        if (age == null) {
            return null;
        }
        
        // Global average life expectancy (approximately 73 years)
        int averageLifeExpectancy = 73;
        int remainingYears = Math.max(0, averageLifeExpectancy - age);
        
        return LocalDate.now().plusYears(remainingYears);
    }

    /**
     * Gets the customer's full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
} 