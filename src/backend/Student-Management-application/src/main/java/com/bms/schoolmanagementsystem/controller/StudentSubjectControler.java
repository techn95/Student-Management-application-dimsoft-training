package com.bms.schoolmanagementsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bms.schoolmanagementsystem.dto.request.studentsubject.CreateStudentSubjectRequest;
import com.bms.schoolmanagementsystem.exception.studentsubject.StudentSubjectAlreadyExistException;
import com.bms.schoolmanagementsystem.model.StudentSubject;
import com.bms.schoolmanagementsystem.service.StudentSubjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/student-subject")
@Tag(name = "Student-Subject", description = "Student-Subject API")
public class StudentSubjectControler {

    private StudentSubjectService studentSubjectService;

    public StudentSubjectControler(StudentSubjectService studentSubjectService) {
        this.studentSubjectService = studentSubjectService;
    }

    @Operation(summary = "Create StudentSubject", description = "Create a new StudentSubject", tags = {"Student-Subject"})
    @PostMapping
    public ResponseEntity<StudentSubject> createStudentSubject(@Valid @RequestBody CreateStudentSubjectRequest request)
            throws StudentSubjectAlreadyExistException {

        StudentSubject studentSubject = this.studentSubjectService.createStudentSubject(request);
        return new ResponseEntity<StudentSubject>(studentSubject, HttpStatus.CREATED);
    }

    @Operation(summary = "Get All StudentSubjects", description = "Get all StudentSubjects", tags = {"Student-Subject"})
    @GetMapping
    public ResponseEntity<List<StudentSubject>> getAllStudentSubjects() {

        List<StudentSubject> studentSubjects = this.studentSubjectService.findAllStudentSubjects();
        return new ResponseEntity<List<StudentSubject>>(studentSubjects, HttpStatus.OK);
    }

    @Operation(summary = "Delete StudentSubject", description = "Delete a StudentSubject by id", tags = {"Student-Subject"})
    @DeleteMapping
    public ResponseEntity<StudentSubject> deleteStudentSubject(@RequestParam("id") String id) {

        this.studentSubjectService.deleteStudentSubject(id);
        return ResponseEntity.noContent().build();
    }

}
