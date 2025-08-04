package com.seek.test.seek_test.service;

import com.seek.test.seek_test.dto.CustomerPageResponseDto;
import com.seek.test.seek_test.entity.Customer;
import com.seek.test.seek_test.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServicePaginationTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private MonitoringService monitoringService;

    @InjectMocks
    private CustomerService customerService;

    private List<Customer> sampleCustomers;

    @BeforeEach
    void setUp() {
        // Create sample customers
        sampleCustomers = Arrays.asList(
            createCustomer(1L, "John", "Doe", 30),
            createCustomer(2L, "Jane", "Smith", 25),
            createCustomer(3L, "Mike", "Johnson", 35),
            createCustomer(4L, "Sarah", "Williams", 28),
            createCustomer(5L, "David", "Brown", 42),
            createCustomer(6L, "Emily", "Davis", 31),
            createCustomer(7L, "Robert", "Miller", 29),
            createCustomer(8L, "Lisa", "Wilson", 33),
            createCustomer(9L, "James", "Taylor", 27),
            createCustomer(10L, "Amanda", "Anderson", 36),
            createCustomer(11L, "Chris", "Thomas", 24),
            createCustomer(12L, "Jessica", "Jackson", 32)
        );
    }

    @Test
    void testPaginationWithValidParameters() {
        // Given
        int page = 1; // 1-based page
        int size = 5;
        Pageable pageable = PageRequest.of(0, size); // 0-based for Spring Data
        Page<Customer> customerPage = new PageImpl<>(
            sampleCustomers.subList(0, 5), 
            pageable, 
            sampleCustomers.size()
        );

        when(customerRepository.countByIsDeletedFalse()).thenReturn((long) sampleCustomers.size());
        when(customerRepository.findByIsDeletedFalse(any(Pageable.class))).thenReturn(customerPage);

        // When
        CustomerPageResponseDto result = customerService.getAllCustomersPaginated(page, size);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getPageNumber()); // Should be 1-based
        assertEquals(5, result.getPageSize());
        assertEquals(12, result.getTotalElements());
        assertEquals(3, result.getTotalPages()); // 12 elements / 5 per page = 3 pages
        assertTrue(result.isFirst());
        assertFalse(result.isLast());
        assertTrue(result.isHasNext());
        assertFalse(result.isHasPrevious());
        assertEquals(5, result.getContent().size());
    }

    @Test
    void testPaginationWithNullParameters() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customerPage = new PageImpl<>(
            sampleCustomers.subList(0, 10), 
            pageable, 
            sampleCustomers.size()
        );

        when(customerRepository.countByIsDeletedFalse()).thenReturn((long) sampleCustomers.size());
        when(customerRepository.findByIsDeletedFalse(any(Pageable.class))).thenReturn(customerPage);

        // When
        CustomerPageResponseDto result = customerService.getAllCustomersPaginated(null, null);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getPageNumber()); // Should default to page 1 (1-based)
        assertEquals(10, result.getPageSize()); // Should default to 10
        assertEquals(12, result.getTotalElements());
        assertEquals(2, result.getTotalPages()); // 12 elements / 10 per page = 2 pages
    }

    @Test
    void testPaginationWithPageExceedingTotalPages() {
        // Given
        int requestedPage = 10; // Request page 10 (1-based)
        int size = 5;
        int totalPages = 3; // But only 3 pages exist
        int actualPage = totalPages - 1; // Should use page 2 (0-based: 2, 1-based: 3)
        
        Pageable pageable = PageRequest.of(actualPage, size);
        Page<Customer> customerPage = new PageImpl<>(
            sampleCustomers.subList(10, 12), // Last 2 elements
            pageable, 
            sampleCustomers.size()
        );

        when(customerRepository.countByIsDeletedFalse()).thenReturn((long) sampleCustomers.size());
        when(customerRepository.findByIsDeletedFalse(any(Pageable.class))).thenReturn(customerPage);

        // When
        CustomerPageResponseDto result = customerService.getAllCustomersPaginated(requestedPage, size);

        // Then
        assertNotNull(result);
        assertEquals(3, result.getPageNumber()); // Should be page 3 (1-based, last page)
        assertEquals(5, result.getPageSize());
        assertEquals(12, result.getTotalElements());
        assertEquals(3, result.getTotalPages());
        assertFalse(result.isFirst());
        assertTrue(result.isLast());
        assertFalse(result.isHasNext());
        assertTrue(result.isHasPrevious());
        assertEquals(2, result.getContent().size()); // Last 2 elements
    }

    @Test
    void testPaginationWithInvalidSize() {
        // Given
        int page = 0;
        int invalidSize = -5; // Invalid size
        int validSize = 10; // Should default to 10
        
        Pageable pageable = PageRequest.of(page, validSize);
        Page<Customer> customerPage = new PageImpl<>(
            sampleCustomers.subList(0, 10), 
            pageable, 
            sampleCustomers.size()
        );

        when(customerRepository.countByIsDeletedFalse()).thenReturn((long) sampleCustomers.size());
        when(customerRepository.findByIsDeletedFalse(any(Pageable.class))).thenReturn(customerPage);

        // When
        CustomerPageResponseDto result = customerService.getAllCustomersPaginated(page, invalidSize);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getPageNumber()); // Should be 1-based
        assertEquals(10, result.getPageSize()); // Should default to 10
        assertEquals(12, result.getTotalElements());
    }

    @Test
    void testPaginationWithSizeExceedingMaximum() {
        // Given
        int page = 1; // 1-based page
        int oversizedSize = 150; // Exceeds maximum of 100
        int validSize = 10; // Should default to 10
        
        Pageable pageable = PageRequest.of(0, validSize); // 0-based for Spring Data
        Page<Customer> customerPage = new PageImpl<>(
            sampleCustomers.subList(0, 10), 
            pageable, 
            sampleCustomers.size()
        );

        when(customerRepository.countByIsDeletedFalse()).thenReturn((long) sampleCustomers.size());
        when(customerRepository.findByIsDeletedFalse(any(Pageable.class))).thenReturn(customerPage);

        // When
        CustomerPageResponseDto result = customerService.getAllCustomersPaginated(page, oversizedSize);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getPageNumber()); // Should be 1-based
        assertEquals(10, result.getPageSize()); // Should default to 10
        assertEquals(12, result.getTotalElements());
    }

    @Test
    void testPaginationWithPageZero() {
        // Given
        int page = 0; // Page 0 should be converted to page 1
        int size = 5;
        
        Pageable pageable = PageRequest.of(0, size); // 0-based for Spring Data
        Page<Customer> customerPage = new PageImpl<>(
            sampleCustomers.subList(0, 5), 
            pageable, 
            sampleCustomers.size()
        );

        when(customerRepository.countByIsDeletedFalse()).thenReturn((long) sampleCustomers.size());
        when(customerRepository.findByIsDeletedFalse(any(Pageable.class))).thenReturn(customerPage);

        // When
        CustomerPageResponseDto result = customerService.getAllCustomersPaginated(page, size);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getPageNumber()); // Should be converted to page 1
        assertEquals(5, result.getPageSize());
        assertEquals(12, result.getTotalElements());
        assertEquals(3, result.getTotalPages());
    }

    private Customer createCustomer(Long id, String firstName, String lastName, int age) {
        return Customer.builder()
            .id(id)
            .firstName(firstName)
            .lastName(lastName)
            .age(age)
            .birthDate(LocalDate.now().minusYears(age))
            .isDeleted(false)
            .build();
    }
} 