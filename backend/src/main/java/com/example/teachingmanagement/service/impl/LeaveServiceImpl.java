package com.example.teachingmanagement.service.impl;

import com.example.teachingmanagement.entity.Leave;
import com.example.teachingmanagement.repository.LeaveRepository;
import com.example.teachingmanagement.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Override
    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    @Override
    public List<Leave> getLeavesByStudentId(Integer studentId) {
        return leaveRepository.findByStudentId(studentId);
    }

    @Override
    public List<Leave> getLeavesByStatus(Leave.LeaveStatus status) {
        return leaveRepository.findByStatus(status);
    }

    @Override
    public Optional<Leave> getLeaveById(Integer id) {
        return leaveRepository.findById(id);
    }

    @Override
    public Leave saveLeave(Leave leave) {
        return leaveRepository.save(leave);
    }

    @Override
    public Leave approveLeave(Integer id) {
        Optional<Leave> optionalLeave = leaveRepository.findById(id);
        if (optionalLeave.isPresent()) {
            Leave leave = optionalLeave.get();
            leave.setStatus(Leave.LeaveStatus.approved);
            return leaveRepository.save(leave);
        }
        return null;
    }

    @Override
    public Leave rejectLeave(Integer id) {
        Optional<Leave> optionalLeave = leaveRepository.findById(id);
        if (optionalLeave.isPresent()) {
            Leave leave = optionalLeave.get();
            leave.setStatus(Leave.LeaveStatus.rejected);
            return leaveRepository.save(leave);
        }
        return null;
    }

    @Override
    public void deleteLeave(Integer id) {
        leaveRepository.deleteById(id);
    }
}    