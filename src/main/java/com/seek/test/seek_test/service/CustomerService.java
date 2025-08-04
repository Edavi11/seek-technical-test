package com.seek.test.seek_test.service;

import com.seek.test.seek_test.dto.CustomerMetricsDto;
import com.seek.test.seek_test.dto.CustomerPageResponseDto;
import com.seek.test.seek_test.dto.CustomerRequestDto;
import com.seek.test.seek_test.dto.CustomerResponseDto;
import com.seek.test.seek_test.dto.DeleteResponseDto;
import com.seek.test.seek_test.entity.Customer;
import com.seek.test.seek_test.exception.AgeBirthDateMismatchException;
import com.seek.test.seek_test.exception.CustomerNotFoundException;
import com.seek.test.seek_test.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.core.instrument.Timer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final MonitoringService monitoringService;

    /**
     * Creates a new customer
     */
    public CustomerResponseDto createCustomer(CustomerRequestDto requestDto) {
        log.info("Creating new customer: {}", requestDto.getFirstName() + " " + requestDto.getLastName());
        
        Timer.Sample timer = monitoringService.startCustomerCreationTimer();
        
        try {
            Customer customer = Customer.builder()
                    .firstName(requestDto.getFirstName())
                    .lastName(requestDto.getLastName())
                    .age(requestDto.getAge())
                    .birthDate(requestDto.getBirthDate())
                    .build();

            // Validate age matches birth date
            customer.validateAgeMatchesBirthDate();

            Customer savedCustomer = customerRepository.save(customer);
            log.info("Customer created successfully with ID: {}", savedCustomer.getId());
            
            // Record metrics
            monitoringService.recordCustomerCreated();
            monitoringService.recordCustomMetric("customer.total.count", customerRepository.countByIsDeletedFalse());
            
            return mapToResponseDto(savedCustomer);
        } finally {
            monitoringService.stopCustomerCreationTimer(timer);
        }
    }

    /**
     * Gets all active customers
     */
    @Transactional(readOnly = true)
    public List<CustomerResponseDto> getAllCustomers() {
        log.info("Getting all active customers");
        
        Timer.Sample timer = monitoringService.startCustomerRetrievalTimer();
        
        try {
            List<Customer> customers = customerRepository.findByIsDeletedFalse();
            monitoringService.recordCustomerRetrieved();
            return customers.stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());
        } finally {
            monitoringService.stopCustomerRetrievalTimer(timer);
        }
    }

    /**
     * Gets all active customers with pagination
     */
    @Transactional(readOnly = true)
    public CustomerPageResponseDto getAllCustomersPaginated(Integer page, Integer size) {
        log.info("Getting customers with pagination - page: {}, size: {}", page, size);
        
        Timer.Sample timer = monitoringService.startCustomerRetrievalTimer();
        
        try {
            // Validate and set default values for pagination parameters
            int validSize = (size != null && size > 0 && size <= 100) ? size : 10;
            
            // First, get the total count to calculate total pages
            long totalElements = customerRepository.countByIsDeletedFalse();
            int totalPages = (int) Math.ceil((double) totalElements / validSize);
            
            // Convert 1-based page to 0-based for Spring Data
            // If page is null, 0, or negative, default to page 1 (0-based: 0)
            int zeroBasedPage;
            if (page == null || page <= 0) {
                zeroBasedPage = 0; // Page 1 in 1-based = Page 0 in 0-based
                log.info("Using default page 1 (0-based: 0)");
            } else {
                // Convert 1-based to 0-based
                zeroBasedPage = page - 1;
                
                // Check if requested page exceeds total pages
                if (zeroBasedPage >= totalPages && totalPages > 0) {
                    zeroBasedPage = totalPages - 1; // Use last available page
                    log.info("Requested page {} (1-based) is beyond total pages ({}), using last page: {} (1-based: {})", 
                            page, totalPages, zeroBasedPage, zeroBasedPage + 1);
                }
            }
            
            Pageable pageable = PageRequest.of(zeroBasedPage, validSize);
            Page<Customer> customerPage = customerRepository.findByIsDeletedFalse(pageable);
            
            List<CustomerResponseDto> customers = customerPage.getContent().stream()
                    .map(this::mapToResponseDto)
                    .collect(Collectors.toList());
            
            monitoringService.recordCustomerRetrieved();
            
            return CustomerPageResponseDto.builder()
                    .content(customers)
                    .pageNumber(customerPage.getNumber() + 1) // Convert back to 1-based for response
                    .pageSize(customerPage.getSize())
                    .totalElements(customerPage.getTotalElements())
                    .totalPages(customerPage.getTotalPages())
                    .first(customerPage.isFirst())
                    .last(customerPage.isLast())
                    .hasNext(customerPage.hasNext())
                    .hasPrevious(customerPage.hasPrevious())
                    .build();
        } finally {
            monitoringService.stopCustomerRetrievalTimer(timer);
        }
    }

    /**
     * Gets a customer by ID
     */
    @Transactional(readOnly = true)
    public CustomerResponseDto getCustomerById(Long id) {
        log.info("Searching for customer with ID: {}", id);
        Customer customer = customerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
        
        return mapToResponseDto(customer);
    }

    /**
     * Updates an existing customer
     */
    public CustomerResponseDto updateCustomer(Long id, CustomerRequestDto requestDto) {
        log.info("Updating customer with ID: {}", id);
        
        Customer existingCustomer = customerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        existingCustomer.setFirstName(requestDto.getFirstName());
        existingCustomer.setLastName(requestDto.getLastName());
        existingCustomer.setAge(requestDto.getAge());
        existingCustomer.setBirthDate(requestDto.getBirthDate());

        // Validate age matches birth date
        existingCustomer.validateAgeMatchesBirthDate();

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        log.info("Customer updated successfully with ID: {}", updatedCustomer.getId());
        
        return mapToResponseDto(updatedCustomer);
    }

    /**
     * Logically deletes a customer
     */
    public DeleteResponseDto deleteCustomer(Long id) {
        log.info("Deleting customer with ID: {}", id);
        
        Customer customer = customerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        // Store customer info before deletion
        String customerName = customer.getFullName();
        Long customerId = customer.getId();

        customer.setIsDeleted(true);
        customerRepository.save(customer);
        
        log.info("Customer deleted successfully with ID: {} - Name: {}", id, customerName);
        
        return DeleteResponseDto.builder()
                .message("Customer '" + customerName + "' deleted successfully")
                .customerId(customerId)
                .deletedAt(LocalDateTime.now())
                .status("SUCCESS")
                .build();
    }

    /**
     * Gets customer metrics
     */
    @Transactional(readOnly = true)
    public CustomerMetricsDto getCustomerMetrics() {
        log.info("Calculating customer metrics");
        
        Timer.Sample timer = monitoringService.startMetricsCalculationTimer();
        
        try {
            Long totalCustomers = customerRepository.countByIsDeletedFalse();
            Double averageAge = customerRepository.getAverageAge();
            Double standardDeviation = customerRepository.getStandardDeviation();
            Integer minAge = customerRepository.getMinAge();
            Integer maxAge = customerRepository.getMaxAge();
            List<Integer> agesOrdered = customerRepository.getAgesOrdered();
            
            Double medianAge = calculateMedian(agesOrdered);
            
            CustomerMetricsDto metrics = CustomerMetricsDto.builder()
                    .totalCustomers(totalCustomers)
                    .averageAge(averageAge != null ? Math.round(averageAge * 100.0) / 100.0 : 0.0)
                    .standardDeviation(standardDeviation != null ? Math.round(standardDeviation * 100.0) / 100.0 : 0.0)
                    .minAge(minAge)
                    .maxAge(maxAge)
                    .medianAge(medianAge != null ? Math.round(medianAge * 100.0) / 100.0 : 0.0)
                    .build();

            log.info("Metrics calculated: total={}, average={}, std_dev={}", 
                    totalCustomers, averageAge, standardDeviation);
            
            // Record metrics
            monitoringService.recordMetricsRequested();
            monitoringService.recordCustomMetric("customer.metrics.average_age", averageAge != null ? averageAge : 0.0);
            monitoringService.recordCustomMetric("customer.metrics.total_count", totalCustomers);
            
            return metrics;
        } finally {
            monitoringService.stopMetricsCalculationTimer(timer);
        }
    }



    /**
     * Maps a Customer entity to CustomerResponseDto
     */
    private CustomerResponseDto mapToResponseDto(Customer customer) {
        return CustomerResponseDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .fullName(customer.getFullName())
                .age(customer.getAge())
                .birthDate(customer.getBirthDate())
                .estimatedLifeExpectancy(customer.calculateEstimatedLifeExpectancy())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    /**
     * Calculates the median of a list of numbers
     */
    private Double calculateMedian(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return null;
        }

        int size = numbers.size();
        if (size % 2 == 0) {
            // If the size is even, the median is the average of the two central values
            int mid1 = numbers.get(size / 2 - 1);
            int mid2 = numbers.get(size / 2);
            return (double) (mid1 + mid2) / 2;
        } else {
            // If the size is odd, the median is the central value
            return (double) numbers.get(size / 2);
        }
    }
} 