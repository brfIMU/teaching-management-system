package com.example.teachingmanagement.service;

import com.example.teachingmanagement.entity.Assignment;

import java.util.List;
import java.util.Optional;

public interface AssignmentService {
    List<Assignment> getAllAssignments();
    List<Assignment> getAssignmentsByCourseId(Integer courseId);
    Optional<Assignment> getAssignmentById(Integer id);
    Assignment saveAssignment(Assignment assignment);
    void deleteAssignment(Integer id);
}    