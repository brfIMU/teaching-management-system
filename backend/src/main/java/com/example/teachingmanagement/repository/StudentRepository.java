package com.example.teachingmanagement.repository;

import com.example.teachingmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByStudentId(String studentId);
}    