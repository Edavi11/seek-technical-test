package com.seek.test.seek_test.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Password change request")
public class PasswordChangeRequestDto {
    
    @NotBlank(message = "Current password is required")
    @Schema(description = "Current password", example = "currentPassword123")
    private String currentPassword;
    
    @NotBlank(message = "New password is required")
    @Size(min = 6, message = "New password must be at least 6 characters")
    @Schema(description = "New password (minimum 6 characters)", example = "newPassword123")
    private String newPassword;
    
    @NotBlank(message = "Password confirmation is required")
    @Schema(description = "Confirm new password", example = "newPassword123")
    private String confirmPassword;
    
    // Default constructor
    public PasswordChangeRequestDto() {}
    
    // Constructor with parameters
    public PasswordChangeRequestDto(String currentPassword, String newPassword, String confirmPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }
    
    // Getters and Setters
    public String getCurrentPassword() {
        return currentPassword;
    }
    
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    /**
     * Validates that new password and confirmation match
     * @return true if passwords match, false otherwise
     */
    @JsonIgnore
    public boolean isPasswordConfirmationValid() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
    
    /**
     * Validates that new password is different from current password
     * @return true if passwords are different, false otherwise
     */
    @JsonIgnore
    public boolean isNewPasswordDifferent() {
        return currentPassword != null && newPassword != null && !currentPassword.equals(newPassword);
    }
} 