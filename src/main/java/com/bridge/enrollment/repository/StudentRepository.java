package com.bridge.enrollment.repository;

import com.bridge.enrollment.model.Student;
import com.bridge.enrollment.model.Student.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Student> findByStatus(EnrollmentStatus status);

    @Query("SELECT s FROM Student s WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> searchByName(@Param("name") String name);

    @Query("SELECT s FROM Student s WHERE s.email = :email AND s.id <> :id")
    Optional<Student> findByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);
}
