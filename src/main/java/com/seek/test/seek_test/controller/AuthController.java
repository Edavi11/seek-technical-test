package com.seek.test.seek_test.controller;

import com.seek.test.seek_test.dto.AuthRequestDto;
import com.seek.test.seek_test.dto.AuthResponseDto;
import com.seek.test.seek_test.exception.ErrorResponse;
import com.seek.test.seek_test.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and get JWT token", 
               description = "Authenticate user with username and password to receive JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Authentication successful",
            content = @Content(schema = @Schema(implementation = AuthResponseDto.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Successful Authentication",
                        value = "{\n" +
                                "  \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1NDI0MDYyOSwiZXhwIjoxNzU0MzI3MDI5fQ.vJaD0yTzyv2ZvnXjShx2iL8XSKHbo9bPHqBrhsit6RM\"\n" +
                                "}"
                    )
                })),
        @ApiResponse(responseCode = "401", description = "Invalid credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Invalid Credentials",
                        value = "{\n" +
                                "  \"timestamp\": \"2025-01-03T13:31:23.537\",\n" +
                                "  \"status\": 401,\n" +
                                "  \"error\": \"Unauthorized\",\n" +
                                "  \"message\": \"Invalid username or password\",\n" +
                                "  \"path\": \"/api/v1/auth/login\",\n" +
                                "  \"details\": null\n" +
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
                                "  \"path\": \"/api/v1/auth/login\",\n" +
                                "  \"details\": null\n" +
                                "}"
                    )
                }))
    })
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtService.generateToken(authentication.getName());
        
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
} 