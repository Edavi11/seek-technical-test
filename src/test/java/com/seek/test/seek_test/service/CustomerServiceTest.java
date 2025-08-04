package com.seek.test.seek_test.service;

import com.seek.test.seek_test.dto.CustomerRequestDto;
import com.seek.test.seek_test.dto.CustomerResponseDto;
import com.seek.test.seek_test.dto.DeleteResponseDto;
import com.seek.test.seek_test.entity.Customer;
import com.seek.test.seek_test.exception.CustomerNotFoundException;
import com.seek.test.seek_test.repository.CustomerRepository;
import io.micrometer.core.instrument.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private MonitoringService monitoringService;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;
    private CustomerRequestDto testCustomerRequest;

    @BeforeEach
    void setUp() {
        // Calculate correct birth date for age 30 (as of 2024)
        LocalDate birthDate = LocalDate.now().minusYears(30);
        
        testCustomer = Customer.builder()
            .id(1L)
            .firstName("Juan")
            .lastName("Pérez")
            .age(30)
            .birthDate(birthDate)
            .isDeleted(false)
            .build();

        testCustomerRequest = CustomerRequestDto.builder()
            .firstName("Juan")
            .lastName("Pérez")
            .age(30)
            .birthDate(birthDate)
            .build();

        // Use lenient mocks to avoid unnecessary stubbing errors
        lenient().when(monitoringService.startCustomerCreationTimer()).thenReturn(mock(Timer.Sample.class));
        lenient().when(monitoringService.startCustomerRetrievalTimer()).thenReturn(mock(Timer.Sample.class));
    }

    @Test
    void createCustomer_Success() {
        // Given
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // When
        CustomerResponseDto result = customerService.createCustomer(testCustomerRequest);

        // Then
        assertNotNull(result);
        assertEquals(testCustomer.getFirstName(), result.getFirstName());
        assertEquals(testCustomer.getLastName(), result.getLastName());
        assertEquals(testCustomer.getAge(), result.getAge());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void getAllCustomers_Success() {
        // Given
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByIsDeletedFalse()).thenReturn(customers);

        // When
        List<CustomerResponseDto> result = customerService.getAllCustomers();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCustomer.getFirstName(), result.get(0).getFirstName());
        verify(customerRepository).findByIsDeletedFalse();
    }

    @Test
    void getCustomerById_Success() {
        // Given
        when(customerRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(testCustomer));

        // When
        CustomerResponseDto result = customerService.getCustomerById(1L);

        // Then
        assertNotNull(result);
        assertEquals(testCustomer.getFirstName(), result.getFirstName());
        verify(customerRepository).findByIdAndIsDeletedFalse(1L);
    }

    @Test
    void getCustomerById_NotFound() {
        // Given
        when(customerRepository.findByIdAndIsDeletedFalse(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerById(999L);
        });
        verify(customerRepository).findByIdAndIsDeletedFalse(999L);
    }

    @Test
    void updateCustomer_Success() {
        // Given
        // Calculate correct birth date for age 31
        LocalDate updatedBirthDate = LocalDate.now().minusYears(31);
        
        CustomerRequestDto updateRequest = CustomerRequestDto.builder()
            .firstName("Juan Updated")
            .lastName("Pérez Updated")
            .age(31)
            .birthDate(updatedBirthDate)
            .build();

        Customer updatedCustomer = Customer.builder()
            .id(1L)
            .firstName("Juan Updated")
            .lastName("Pérez Updated")
            .age(31)
            .birthDate(updatedBirthDate)
            .isDeleted(false)
            .build();

        when(customerRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        // When
        CustomerResponseDto result = customerService.updateCustomer(1L, updateRequest);

        // Then
        assertNotNull(result);
        assertEquals("Juan Updated", result.getFirstName());
        assertEquals("Pérez Updated", result.getLastName());
        assertEquals(31, result.getAge());
        verify(customerRepository).findByIdAndIsDeletedFalse(1L);
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void deleteCustomer_Success() {
        // Given
        when(customerRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // When
        DeleteResponseDto result = customerService.deleteCustomer(1L);

        // Then
        assertNotNull(result);
        assertEquals("Customer 'Juan Pérez' deleted successfully", result.getMessage());
        assertEquals(1L, result.getCustomerId());
        assertEquals("SUCCESS", result.getStatus());
        assertNotNull(result.getDeletedAt());
        verify(customerRepository).findByIdAndIsDeletedFalse(1L);
        verify(customerRepository).save(any(Customer.class));
    }
} 