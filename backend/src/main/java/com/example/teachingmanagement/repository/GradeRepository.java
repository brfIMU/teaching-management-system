package com.example.teachingmanagement.repository;

import com.example.teachingmanagement.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> findByStudentId(Integer studentId);
    List<Grade> findByCourseId(Integer courseId);
    List<Grade> findByAssignmentId(Integer assignmentId);
}    