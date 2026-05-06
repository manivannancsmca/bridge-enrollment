package com.bridge.enrollment.dto;

import com.bridge.enrollment.model.Student.EnrollmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request payload for creating or updating a student")
public class StudentRequestDTO {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    @Schema(description = "Student's first name", example = "John")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    @Schema(description = "Student's last name", example = "Doe")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Schema(description = "Student's email address", example = "john.doe@example.com")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number must be valid (10-15 digits)")
    @Schema(description = "Student's phone number", example = "+919876543210")
    private String phone;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Schema(description = "Student's date of birth", example = "2000-05-15")
    private LocalDate dateOfBirth;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    @Schema(description = "Student's address", example = "123 Main St, Chennai, Tamil Nadu")
    private String address;

    @Schema(description = "Enrollment status", example = "ACTIVE",
            allowableValues = {"ACTIVE", "INACTIVE", "PENDING", "GRADUATED", "SUSPENDED"})
    private EnrollmentStatus status;
}
