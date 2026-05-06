package com.bridge.enrollment.dto;

import com.bridge.enrollment.model.Student.EnrollmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response payload for student data")
public class StudentResponseDTO {

    @Schema(description = "Unique student ID", example = "1")
    private Long id;

    @Schema(description = "Student's first name", example = "John")
    private String firstName;

    @Schema(description = "Student's last name", example = "Doe")
    private String lastName;

    @Schema(description = "Student's full name", example = "John Doe")
    private String fullName;

    @Schema(description = "Student's email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Student's phone number", example = "+919876543210")
    private String phone;

    @Schema(description = "Student's date of birth", example = "2000-05-15")
    private LocalDate dateOfBirth;

    @Schema(description = "Student's address", example = "123 Main St, Chennai, Tamil Nadu")
    private String address;

    @Schema(description = "Enrollment status", example = "ACTIVE")
    private EnrollmentStatus status;

    @Schema(description = "Record creation timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Record last update timestamp")
    private LocalDateTime updatedAt;
}
