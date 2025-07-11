package com.example.teachingmanagement.service;

import com.example.teachingmanagement.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudents();
    Optional<Student> getStudentById(Integer id);
    Optional<Student> getStudentByStudentId(String studentId);
    Student saveStudent(Student student);
    void deleteStudent(Integer id);
}    