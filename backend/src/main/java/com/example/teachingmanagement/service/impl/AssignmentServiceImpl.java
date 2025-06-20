package com.example.teachingmanagement.service.impl;

import com.example.teachingmanagement.entity.Assignment;
import com.example.teachingmanagement.repository.AssignmentRepository;
import com.example.teachingmanagement.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public List<Assignment> getAssignmentsByCourseId(Integer courseId) {
        return assignmentRepository.findByCourseId(courseId);
    }

    @Override
    public Optional<Assignment> getAssignmentById(Integer id) {
        return assignmentRepository.findById(id);
    }

    @Override
    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    @Override
    public void deleteAssignment(Integer id) {
        assignmentRepository.deleteById(id);
    }
}    