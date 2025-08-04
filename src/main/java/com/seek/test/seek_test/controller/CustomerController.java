package com.seek.test.seek_test.controller;

import com.seek.test.seek_test.dto.CustomerMetricsDto;
import com.seek.test.seek_test.dto.CustomerPageResponseDto;
import com.seek.test.seek_test.dto.CustomerRequestDto;
import com.seek.test.seek_test.dto.CustomerResponseDto;
import com.seek.test.seek_test.dto.DeleteResponseDto;
import com.seek.test.seek_test.exception.ErrorResponse;
import com.seek.test.seek_test.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Customer Management", description = "APIs for managing customers")
@SecurityRequirement(name = "Bearer Authentication")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @Operation(summary = "Create a new customer", description = "Creates a new customer with the provided data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Customer created successfully",
            content = @Content(schema = @Schema(implementation = CustomerResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "422", description = "Business validation error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Age Birth Date Mismatch",
                        value = "{\n" +
                                "  \"timestamp\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": 422,\n" +
                                "  \"error\": \"Unprocessable Entity\",\n" +
                                "  \"message\": \"Age mismatch: provided age is 25, but calculated age from birth date 1990-01-15 is 33\",\n" +
                                "  \"path\": \"/api/v1/customers\",\n" +
                                "  \"details\": null\n" +
                                "}"
                    )
                }))
    })
    public ResponseEntity<CustomerResponseDto> createCustomer(
            @Valid @RequestBody CustomerRequestDto requestDto) {
        log.info("Received request to create customer: {}", requestDto.getFirstName());
        CustomerResponseDto createdCustomer = customerService.createCustomer(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @GetMapping
    @Operation(summary = "Get all customers with pagination", 
               description = "Returns a paginated list of all active customers. Default: page 1, size 10. Max size: 100. " +
                            "If requested page exceeds total pages, returns the last available page.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer list retrieved successfully",
            content = @Content(schema = @Schema(implementation = CustomerPageResponseDto.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Paginated Customers",
                        value = "{\n" +
                                "  \"content\": [\n" +
                                "    {\n" +
                                "      \"id\": 1,\n" +
                                "      \"firstName\": \"John\",\n" +
                                "      \"lastName\": \"Doe\",\n" +
                                "      \"fullName\": \"John Doe\",\n" +
                                "      \"age\": 30,\n" +
                                "      \"birthDate\": \"1993-01-15\",\n" +
                                "      \"estimatedLifeExpectancy\": \"2063-01-15\",\n" +
                                "      \"createdAt\": \"2025-01-03T10:00:00\",\n" +
                                "      \"updatedAt\": \"2025-01-03T10:00:00\"\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"pageNumber\": 1,\n" +
                                "  \"pageSize\": 10,\n" +
                                "  \"totalElements\": 25,\n" +
                                "  \"totalPages\": 3,\n" +
                                "  \"first\": true,\n" +
                                "  \"last\": false,\n" +
                                "  \"hasNext\": true,\n" +
                                "  \"hasPrevious\": false\n" +
                                "}"
                    )
                })),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CustomerPageResponseDto> getAllCustomers(
            @Parameter(description = "Page number (1-based). Default: 1. If 0 or negative, uses page 1. If exceeds total pages, returns last page", example = "1") 
            @RequestParam(value = "page", required = false) Integer page,
            @Parameter(description = "Number of elements per page (1-100). Default: 10", example = "10") 
            @RequestParam(value = "size", required = false) Integer size) {
        log.info("Received request to get customers with pagination - page: {}, size: {}", page, size);
        CustomerPageResponseDto customers = customerService.getAllCustomersPaginated(page, size);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID", description = "Returns a specific customer by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer found successfully",
            content = @Content(schema = @Schema(implementation = CustomerResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Customer not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class), 
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Customer Not Found",
                        value = "{\n" +
                                "  \"timestamp\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": 404,\n" +
                                "  \"error\": \"Not Found\",\n" +
                                "  \"message\": \"Customer not found with ID: 40\",\n" +
                                "  \"path\": \"/api/v1/customers/40\",\n" +
                                "  \"details\": null\n" +
                                "}"
                    )
                })),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Internal Server Error",
                        value = "{\n" +
                                "  \"timestamp\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": 500,\n" +
                                "  \"error\": \"Internal Server Error\",\n" +
                                "  \"message\": \"An unexpected error occurred\",\n" +
                                "  \"path\": \"/api/v1/customers/40\",\n" +
                                "  \"details\": null\n" +
                                "}"
                    )
                }))
    })
    public ResponseEntity<CustomerResponseDto> getCustomerById(
            @Parameter(description = "Customer ID") @PathVariable Long id) {
        log.info("Received request to get customer with ID: {}", id);
        CustomerResponseDto customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer", description = "Updates the data of an existing customer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer updated successfully",
            content = @Content(schema = @Schema(implementation = CustomerResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Customer not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "422", description = "Business validation error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Age Birth Date Mismatch",
                        value = "{\n" +
                                "  \"timestamp\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": 422,\n" +
                                "  \"error\": \"Unprocessable Entity\",\n" +
                                "  \"message\": \"Age mismatch: provided age is 25, but calculated age from birth date 1990-01-15 is 33\",\n" +
                                "  \"path\": \"/api/v1/customers/1\",\n" +
                                "  \"details\": null\n" +
                                "}"
                    )
                }))
    })
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @Parameter(description = "Customer ID") @PathVariable Long id,
            @Valid @RequestBody CustomerRequestDto requestDto) {
        log.info("Received request to update customer with ID: {}", id);
        CustomerResponseDto updatedCustomer = customerService.updateCustomer(id, requestDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer", description = "Logically deletes a customer by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer deleted successfully",
            content = @Content(schema = @Schema(implementation = DeleteResponseDto.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Customer Deleted Successfully",
                        value = "{\n" +
                                "  \"message\": \"Customer 'John Doe' deleted successfully\",\n" +
                                "  \"customerId\": 1,\n" +
                                "  \"deletedAt\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": \"SUCCESS\"\n" +
                                "}"
                    )
                })),
        @ApiResponse(responseCode = "404", description = "Customer not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<DeleteResponseDto> deleteCustomer(
            @Parameter(description = "Customer ID") @PathVariable Long id) {
        log.info("Received request to delete customer with ID: {}", id);
        DeleteResponseDto deleteResponse = customerService.deleteCustomer(id);
        return ResponseEntity.ok(deleteResponse);
    }

    @GetMapping("/metrics")
    @Operation(summary = "Get customer metrics", description = "Returns statistics about customers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Metrics retrieved successfully",
            content = @Content(schema = @Schema(implementation = CustomerMetricsDto.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CustomerMetricsDto> getCustomerMetrics() {
        log.info("Received request to get customer metrics");
        CustomerMetricsDto metrics = customerService.getCustomerMetrics();
        return ResponseEntity.ok(metrics);
    }


} 