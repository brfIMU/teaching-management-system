package com.example.teachingmanagement.service;

import com.example.teachingmanagement.entity.Leave;

import java.util.List;
import java.util.Optional;

public interface LeaveService {
    List<Leave> getAllLeaves();
    List<Leave> getLeavesByStudentId(Integer studentId);
    List<Leave> getLeavesByStatus(Leave.LeaveStatus status);
    Optional<Leave> getLeaveById(Integer id);
    Leave saveLeave(Leave leave);
    Leave approveLeave(Integer id);
    Leave rejectLeave(Integer id);
    void deleteLeave(Integer id);
}    