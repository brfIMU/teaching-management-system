package com.example.teachingmanagement.controller;

import com.example.teachingmanagement.entity.Leave;
import com.example.teachingmanagement.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping
    public ResponseEntity<List<Leave>> getAllLeaves() {
        List<Leave> leaves = leaveService.getAllLeaves();
        return new ResponseEntity<>(leaves, HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Leave>> getLeavesByStudentId(@PathVariable Integer studentId) {
        List<Leave> leaves = leaveService.getLeavesByStudentId(studentId);
        return new ResponseEntity<>(leaves, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Leave>> getLeavesByStatus(@PathVariable String status) {
        Leave.LeaveStatus leaveStatus = Leave.LeaveStatus.valueOf(status);
        List<Leave> leaves = leaveService.getLeavesByStatus(leaveStatus);
        return new ResponseEntity<>(leaves, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leave> getLeaveById(@PathVariable Integer id) {
        Optional<Leave> leave = leaveService.getLeaveById(id);
        return leave.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Leave> createLeave(@RequestBody Leave leave) {
        Leave savedLeave = leaveService.saveLeave(leave);
        return new ResponseEntity<>(savedLeave, HttpStatus.CREATED);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Leave> approveLeave(@PathVariable Integer id) {
        Leave approvedLeave = leaveService.approveLeave(id);
        if (approvedLeave != null) {
            return new ResponseEntity<>(approvedLeave, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Leave> rejectLeave(@PathVariable Integer id) {
        Leave rejectedLeave = leaveService.rejectLeave(id);
        if (rejectedLeave != null) {
            return new ResponseEntity<>(rejectedLeave, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Integer id) {
        leaveService.deleteLeave(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}    