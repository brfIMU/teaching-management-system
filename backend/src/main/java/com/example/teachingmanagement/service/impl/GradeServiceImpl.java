package com.example.teachingmanagement.service.impl;

import com.example.teachingmanagement.entity.Grade;
import com.example.teachingmanagement.repository.GradeRepository;
import com.example.teachingmanagement.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    @Override
    public List<Grade> getGradesByStudentId(Integer studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    @Override
    public List<Grade> getGradesByCourseId(Integer courseId) {
        return gradeRepository.findByCourseId(courseId);
    }

    @Override
    public List<Grade> getGradesByAssignmentId(Integer assignmentId) {
        return gradeRepository.findByAssignmentId(assignmentId);
    }

    @Override
    public Optional<Grade> getGradeById(Integer id) {
        return gradeRepository.findById(id);
    }

    @Override
    public Grade saveGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    @Override
    public void deleteGrade(Integer id) {
        gradeRepository.deleteById(id);
    }
}    