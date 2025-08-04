package com.seek.test.seek_test.service;

import com.seek.test.seek_test.dto.CustomerRequestDto;
import com.seek.test.seek_test.entity.Customer;
import com.seek.test.seek_test.exception.AgeBirthDateMismatchException;
import com.seek.test.seek_test.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceValidationTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private MonitoringService monitoringService;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testCreateCustomerWithValidAgeAndBirthDate() {
        // Given
        LocalDate birthDate = LocalDate.now().minusYears(30);
        CustomerRequestDto requestDto = CustomerRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .age(30)
                .birthDate(birthDate)
                .build();

        Customer savedCustomer = Customer.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .age(30)
                .birthDate(birthDate)
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(customerRepository.countByIsDeletedFalse()).thenReturn(1L);

        // When & Then
        assertDoesNotThrow(() -> {
            customerService.createCustomer(requestDto);
        });
    }

    @Test
    void testCreateCustomerWithInvalidAgeAndBirthDate() {
        // Given
        LocalDate birthDate = LocalDate.now().minusYears(30);
        CustomerRequestDto requestDto = CustomerRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .age(25) // Wrong age for birth date
                .birthDate(birthDate)
                .build();

        // When & Then
        AgeBirthDateMismatchException exception = assertThrows(AgeBirthDateMismatchException.class, () -> {
            customerService.createCustomer(requestDto);
        });

        assertTrue(exception.getMessage().contains("Age mismatch"));
        assertTrue(exception.getMessage().contains("provided age is 25"));
        assertTrue(exception.getMessage().contains("calculated age from birth date"));
    }

    @Test
    void testUpdateCustomerWithValidAgeAndBirthDate() {
        // Given
        LocalDate birthDate = LocalDate.now().minusYears(35);
        CustomerRequestDto requestDto = CustomerRequestDto.builder()
                .firstName("Jane")
                .lastName("Smith")
                .age(35)
                .birthDate(birthDate)
                .build();

        Customer existingCustomer = Customer.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Smith")
                .age(30)
                .birthDate(LocalDate.now().minusYears(30))
                .build();

        Customer updatedCustomer = Customer.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Smith")
                .age(35)
                .birthDate(birthDate)
                .build();

        when(customerRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(java.util.Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        // When & Then
        assertDoesNotThrow(() -> {
            customerService.updateCustomer(1L, requestDto);
        });
    }

    @Test
    void testUpdateCustomerWithInvalidAgeAndBirthDate() {
        // Given
        LocalDate birthDate = LocalDate.now().minusYears(40);
        CustomerRequestDto requestDto = CustomerRequestDto.builder()
                .firstName("Jane")
                .lastName("Smith")
                .age(35) // Wrong age for birth date
                .birthDate(birthDate)
                .build();

        Customer existingCustomer = Customer.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Smith")
                .age(30)
                .birthDate(LocalDate.now().minusYears(30))
                .build();

        when(customerRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(java.util.Optional.of(existingCustomer));

        // When & Then
        AgeBirthDateMismatchException exception = assertThrows(AgeBirthDateMismatchException.class, () -> {
            customerService.updateCustomer(1L, requestDto);
        });

        assertTrue(exception.getMessage().contains("Age mismatch"));
        assertTrue(exception.getMessage().contains("provided age is 35"));
        assertTrue(exception.getMessage().contains("calculated age from birth date"));
    }

    @Test
    void testCustomerEntityValidationMethod() {
        // Given
        LocalDate birthDate = LocalDate.now().minusYears(25);
        Customer customer = Customer.builder()
                .firstName("Test")
                .lastName("User")
                .age(25)
                .birthDate(birthDate)
                .build();

        // When & Then
        assertDoesNotThrow(() -> {
            customer.validateAgeMatchesBirthDate();
        });
    }

    @Test
    void testCustomerEntityValidationMethodWithMismatch() {
        // Given
        LocalDate birthDate = LocalDate.now().minusYears(30);
        Customer customer = Customer.builder()
                .firstName("Test")
                .lastName("User")
                .age(25) // Wrong age
                .birthDate(birthDate)
                .build();

        // When & Then
        AgeBirthDateMismatchException exception = assertThrows(AgeBirthDateMismatchException.class, () -> {
            customer.validateAgeMatchesBirthDate();
        });

        assertTrue(exception.getMessage().contains("Age mismatch"));
        assertTrue(exception.getMessage().contains("provided age is 25"));
        assertTrue(exception.getMessage().contains("calculated age from birth date"));
    }
} 