package com.bridge.enrollment.controller;

import com.bridge.enrollment.dto.ApiResponse;
import com.bridge.enrollment.dto.StudentRequestDTO;
import com.bridge.enrollment.dto.StudentResponseDTO;
import com.bridge.enrollment.model.Student.EnrollmentStatus;
import com.bridge.enrollment.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Student Management", description = "APIs for managing student enrollments")
public class StudentController {

    private final StudentService studentService;

    // ── CREATE ────────────────────────────────────────────────────────────────

    @PostMapping
    @Operation(summary = "Create a new student", description = "Register a new student in the enrollment system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201",
                    description = "Student created successfully",
                    content = @Content(schema = @Schema(implementation = StudentResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400",
                    description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409",
                    description = "Student with email already exists")
    })
    public ResponseEntity<ApiResponse<StudentResponseDTO>> createStudent(
            @Valid @RequestBody StudentRequestDTO request) {
        StudentResponseDTO student = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Student created successfully", student));
    }

    // ── READ ALL ──────────────────────────────────────────────────────────────

    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve a list of all enrolled students")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
            description = "List of students retrieved successfully")
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success("Students retrieved successfully", students));
    }

    // ── READ BY ID ────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Retrieve a specific student by their ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Student found successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",
                    description = "Student not found")
    })
    public ResponseEntity<ApiResponse<StudentResponseDTO>> getStudentById(
            @Parameter(description = "Student ID", required = true, example = "1")
            @PathVariable Long id) {
        StudentResponseDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(ApiResponse.success("Student retrieved successfully", student));
    }

    // ── SEARCH BY NAME ────────────────────────────────────────────────────────

    @GetMapping("/search")
    @Operation(summary = "Search students by name", description = "Search students by first or last name (case-insensitive)")
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> searchByName(
            @Parameter(description = "Name to search", required = true, example = "John")
            @RequestParam String name) {
        List<StudentResponseDTO> students = studentService.searchStudentsByName(name);
        return ResponseEntity.ok(ApiResponse.success("Search results retrieved", students));
    }

    // ── FILTER BY STATUS ──────────────────────────────────────────────────────

    @GetMapping("/status/{status}")
    @Operation(summary = "Get students by status", description = "Retrieve all students filtered by enrollment status")
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> getStudentsByStatus(
            @Parameter(description = "Enrollment status", required = true,
                    schema = @Schema(allowableValues = {"ACTIVE", "INACTIVE", "PENDING", "GRADUATED", "SUSPENDED"}))
            @PathVariable EnrollmentStatus status) {
        List<StudentResponseDTO> students = studentService.getStudentsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Students retrieved by status", students));
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────

    @PutMapping("/{id}")
    @Operation(summary = "Update a student", description = "Fully update a student's information")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Student updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",
                    description = "Student not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409",
                    description = "Email already in use by another student")
    })
    public ResponseEntity<ApiResponse<StudentResponseDTO>> updateStudent(
            @Parameter(description = "Student ID", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDTO request) {
        StudentResponseDTO student = studentService.updateStudent(id, request);
        return ResponseEntity.ok(ApiResponse.success("Student updated successfully", student));
    }

    // ── PATCH STATUS ──────────────────────────────────────────────────────────

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update student status", description = "Partially update only the enrollment status of a student")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Status updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",
                    description = "Student not found")
    })
    public ResponseEntity<ApiResponse<StudentResponseDTO>> updateStatus(
            @Parameter(description = "Student ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "New enrollment status", required = true,
                    schema = @Schema(allowableValues = {"ACTIVE", "INACTIVE", "PENDING", "GRADUATED", "SUSPENDED"}))
            @RequestParam EnrollmentStatus status) {
        StudentResponseDTO student = studentService.updateStudentStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Student status updated successfully", student));
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student", description = "Permanently remove a student from the enrollment system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Student deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",
                    description = "Student not found")
    })
    public ResponseEntity<ApiResponse<Void>> deleteStudent(
            @Parameter(description = "Student ID", required = true, example = "1")
            @PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success("Student deleted successfully"));
    }
}
