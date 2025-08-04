package com.seek.test.seek_test.controller;

import com.seek.test.seek_test.dto.UserRequestDto;
import com.seek.test.seek_test.dto.UserCreateResponseDto;
import com.seek.test.seek_test.dto.UserResponseDto;
import com.seek.test.seek_test.dto.UserActionResponseDto;
import com.seek.test.seek_test.dto.PasswordChangeRequestDto;
import com.seek.test.seek_test.exception.ErrorResponse;
import com.seek.test.seek_test.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "User management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user", 
               description = "Create a new user with username and password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully",
            content = @Content(schema = @Schema(implementation = UserCreateResponseDto.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "User Created Successfully",
                        value = "{\n" +
                                "  \"message\": \"User 'john' created successfully\",\n" +
                                "  \"username\": \"john\",\n" +
                                "  \"userId\": 2,\n" +
                                "  \"createdAt\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": \"SUCCESS\"\n" +
                                "}"
                    )
                })),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Bad Request",
                        value = "{\n" +
                                "  \"timestamp\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": 400,\n" +
                                "  \"error\": \"Bad Request\",\n" +
                                "  \"message\": \"Username and password are required\",\n" +
                                "  \"path\": \"/api/v1/users\",\n" +
                                "  \"details\": null\n" +
                                "}"
                    )
                })),
        @ApiResponse(responseCode = "409", description = "Username already exists",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Username Already Exists",
                        value = "{\n" +
                                "  \"timestamp\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": 409,\n" +
                                "  \"error\": \"Conflict\",\n" +
                                "  \"message\": \"Username already exists: admin\",\n" +
                                "  \"path\": \"/api/v1/users\",\n" +
                                "  \"details\": null\n" +
                                "}"
                    )
                }))
    })
    public ResponseEntity<UserCreateResponseDto> createUser(@RequestBody UserRequestDto request) {
        UserCreateResponseDto response = userService.createUser(request.getUsername(), request.getPassword());
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all users", 
               description = "Retrieve list of all users (active and inactive)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
            content = @Content(schema = @Schema(implementation = UserResponseDto.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "All Users",
                        value = "[\n" +
                                "  {\n" +
                                "    \"id\": 1,\n" +
                                "    \"username\": \"admin\",\n" +
                                "    \"isActive\": true,\n" +
                                "    \"createdAt\": \"2025-01-03T10:00:00\",\n" +
                                "    \"updatedAt\": \"2025-01-03T10:00:00\"\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"id\": 2,\n" +
                                "    \"username\": \"john\",\n" +
                                "    \"isActive\": false,\n" +
                                "    \"createdAt\": \"2025-01-03T10:00:00\",\n" +
                                "    \"updatedAt\": \"2025-01-03T10:00:00\"\n" +
                                "  }\n" +
                                "]"
                    )
                })),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active users only", 
               description = "Retrieve list of only active users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Active users retrieved successfully",
            content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<UserResponseDto>> getActiveUsers() {
        List<UserResponseDto> users = userService.findAllActiveUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}/password")
    @Operation(summary = "Update user password", 
               description = "Update password for a specific user by ID. Requires current password validation and new password confirmation.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password updated successfully",
            content = @Content(schema = @Schema(implementation = UserActionResponseDto.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Password Updated Successfully",
                        value = "{\n" +
                                "  \"message\": \"Password updated successfully for user 'admin'\",\n" +
                                "  \"userId\": 1,\n" +
                                "  \"username\": \"admin\",\n" +
                                "  \"action\": \"PASSWORD_UPDATED\",\n" +
                                "  \"performedAt\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": \"SUCCESS\"\n" +
                                "}"
                    )
                })),
        @ApiResponse(responseCode = "400", description = "Password validation error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Current Password Incorrect",
                        value = "{\n" +
                                "  \"timestamp\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": 400,\n" +
                                "  \"error\": \"Bad Request\",\n" +
                                "  \"message\": \"Current password is incorrect\",\n" +
                                "  \"path\": \"/api/v1/users/1/password\",\n" +
                                "  \"details\": null\n" +
                                "}"
                    ),
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Passwords Do Not Match",
                        value = "{\n" +
                                "  \"timestamp\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": 400,\n" +
                                "  \"error\": \"Bad Request\",\n" +
                                "  \"message\": \"New password and confirmation do not match\",\n" +
                                "  \"path\": \"/api/v1/users/1/password\",\n" +
                                "  \"details\": null\n" +
                                "}"
                    ),
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Same Password",
                        value = "{\n" +
                                "  \"timestamp\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": 400,\n" +
                                "  \"error\": \"Bad Request\",\n" +
                                "  \"message\": \"New password must be different from current password\",\n" +
                                "  \"path\": \"/api/v1/users/1/password\",\n" +
                                "  \"details\": null\n" +
                                "}"
                    )
                })),
        @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UserActionResponseDto> updatePassword(
            @PathVariable Long id,
            @Valid @RequestBody PasswordChangeRequestDto request) {
        UserActionResponseDto response = userService.updatePassword(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate user", 
               description = "Deactivate a specific user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deactivated successfully",
            content = @Content(schema = @Schema(implementation = UserActionResponseDto.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "User Deactivated Successfully",
                        value = "{\n" +
                                "  \"message\": \"User 'john' deactivated successfully\",\n" +
                                "  \"userId\": 2,\n" +
                                "  \"username\": \"john\",\n" +
                                "  \"action\": \"DEACTIVATED\",\n" +
                                "  \"performedAt\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": \"SUCCESS\"\n" +
                                "}"
                    )
                })),
        @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UserActionResponseDto> deactivateUser(@PathVariable Long id) {
        UserActionResponseDto response = userService.deactivateUser(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activate user", 
               description = "Activate a specific user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User activated successfully",
            content = @Content(schema = @Schema(implementation = UserActionResponseDto.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "User Activated Successfully",
                        value = "{\n" +
                                "  \"message\": \"User 'john' activated successfully\",\n" +
                                "  \"userId\": 2,\n" +
                                "  \"username\": \"john\",\n" +
                                "  \"action\": \"ACTIVATED\",\n" +
                                "  \"performedAt\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": \"SUCCESS\"\n" +
                                "}"
                    )
                })),
        @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UserActionResponseDto> activateUser(@PathVariable Long id) {
        UserActionResponseDto response = userService.activateUser(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", 
               description = "Permanently delete a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted successfully",
            content = @Content(schema = @Schema(implementation = UserActionResponseDto.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "User Deleted Successfully",
                        value = "{\n" +
                                "  \"message\": \"User 'john' deleted successfully\",\n" +
                                "  \"userId\": 2,\n" +
                                "  \"username\": \"john\",\n" +
                                "  \"action\": \"DELETED\",\n" +
                                "  \"performedAt\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": \"SUCCESS\"\n" +
                                "}"
                    )
                })),
        @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UserActionResponseDto> deleteUser(@PathVariable Long id) {
        UserActionResponseDto response = userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }
} 