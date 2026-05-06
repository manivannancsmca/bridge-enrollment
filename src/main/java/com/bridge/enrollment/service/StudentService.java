package com.bridge.enrollment.service;

import com.bridge.enrollment.dto.StudentRequestDTO;
import com.bridge.enrollment.dto.StudentResponseDTO;
import com.bridge.enrollment.exception.DuplicateResourceException;
import com.bridge.enrollment.exception.ResourceNotFoundException;
import com.bridge.enrollment.model.Student;
import com.bridge.enrollment.model.Student.EnrollmentStatus;
import com.bridge.enrollment.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    // CREATE
    public StudentResponseDTO createStudent(StudentRequestDTO request) {
        log.info("Creating student with email: {}", request.getEmail());

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Student already exists with email: " + request.getEmail());
        }

        Student student = mapToEntity(request);
        Student saved = studentRepository.save(student);

        log.info("Student created successfully with id: {}", saved.getId());
        return mapToResponse(saved);
    }

    // READ ALL
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents() {
        log.info("Fetching all students");
        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // READ BY ID
    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Long id) {
        log.info("Fetching student with id: {}", id);
        Student student = findStudentById(id);
        return mapToResponse(student);
    }

    // READ BY STATUS
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getStudentsByStatus(EnrollmentStatus status) {
        log.info("Fetching students with status: {}", status);
        return studentRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // SEARCH BY NAME
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> searchStudentsByName(String name) {
        log.info("Searching students with name: {}", name);
        return studentRepository.searchByName(name)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // UPDATE
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO request) {
        log.info("Updating student with id: {}", id);

        Student student = findStudentById(id);

        // Check email conflict with other students
        studentRepository.findByEmailAndIdNot(request.getEmail(), id)
                .ifPresent(s -> {
                    throw new DuplicateResourceException(
                            "Email already in use by another student: " + request.getEmail());
                });

        updateEntity(student, request);
        Student updated = studentRepository.save(student);

        log.info("Student updated successfully with id: {}", updated.getId());
        return mapToResponse(updated);
    }

    // DELETE
    public void deleteStudent(Long id) {
        log.info("Deleting student with id: {}", id);
        Student student = findStudentById(id);
        studentRepository.delete(student);
        log.info("Student deleted successfully with id: {}", id);
    }

    // PATCH STATUS
    public StudentResponseDTO updateStudentStatus(Long id, EnrollmentStatus status) {
        log.info("Updating status for student id: {} to {}", id, status);
        Student student = findStudentById(id);
        student.setStatus(status);
        Student updated = studentRepository.save(student);
        return mapToResponse(updated);
    }

    // ---- HELPERS ----

    private Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id));
    }

    private Student mapToEntity(StudentRequestDTO dto) {
        return Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .dateOfBirth(dto.getDateOfBirth())
                .address(dto.getAddress())
                .status(dto.getStatus() != null ? dto.getStatus() : EnrollmentStatus.ACTIVE)
                .build();
    }

    private void updateEntity(Student student, StudentRequestDTO dto) {
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setAddress(dto.getAddress());
        if (dto.getStatus() != null) {
            student.setStatus(dto.getStatus());
        }
    }

    private StudentResponseDTO mapToResponse(Student student) {
        return StudentResponseDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .fullName(student.getFirstName() + " " + student.getLastName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .dateOfBirth(student.getDateOfBirth())
                .address(student.getAddress())
                .status(student.getStatus())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}
