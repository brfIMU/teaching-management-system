package com.example.teachingmanagement.repository;

import com.example.teachingmanagement.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Integer> {
    List<Leave> findByStudentId(Integer studentId);
    List<Leave> findByStatus(Leave.LeaveStatus status);
}    