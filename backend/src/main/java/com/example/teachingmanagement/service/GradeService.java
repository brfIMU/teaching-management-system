package com.example.teachingmanagement.service;

import com.example.teachingmanagement.entity.Grade;

import java.util.List;
import java.util.Optional;

public interface GradeService {
    List<Grade> getAllGrades();
    List<Grade> getGradesByStudentId(Integer studentId);
    List<Grade> getGradesByCourseId(Integer courseId);
    List<Grade> getGradesByAssignmentId(Integer assignmentId);
    Optional<Grade> getGradeById(Integer id);
    Grade saveGrade(Grade grade);
    void deleteGrade(Integer id);
}    