package com.bms.schoolmanagementsystem.controller;

import com.bms.schoolmanagementsystem.dto.StudentDto;
import com.bms.schoolmanagementsystem.dto.converter.StudentDtoConverter;
import com.bms.schoolmanagementsystem.dto.request.student.CreateStudentRequest;
import com.bms.schoolmanagementsystem.dto.request.student.UpdateStudentRequest;
import com.bms.schoolmanagementsystem.model.Student;
import com.bms.schoolmanagementsystem.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Student", description = "Student API")
public class StudentController {
    private final StudentService studentService;
    private final StudentDtoConverter converter;

    public StudentController(StudentService studentService, StudentDtoConverter converter) {
        this.studentService = studentService;
        this.converter = converter;
    }

    @Operation(summary = "Create Student", description = "Create Student", tags = { "Student" })
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@Valid CreateStudentRequest request) {
        Student student = studentService.createStudent(request);
        return new ResponseEntity<>(converter.convert(student), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Student", description = "Update Student by id", tags = { "Student" })
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable String id,
            @Valid UpdateStudentRequest request) {
        Student student = studentService.updateStudent(id, request);
        return ResponseEntity.ok(converter.convert(student));
    }

    @Operation(summary = "Add Student to Classroom", description = "Add Student to Classroom by student id and classroom id", tags = {
            "Student" })
    @PutMapping("/{id}/classroom/{classroomId}")
    public ResponseEntity<StudentDto> addStudentToClassroom(@PathVariable String id, @PathVariable String classroomId) {
        Student student = studentService.addStudentToClassroom(id, classroomId);
        return ResponseEntity.ok(converter.convert(student));
    }

    @Operation(summary = "Remove Student from Classroom", description = "Remove Student from Classroom by student id", tags = {
            "Student" })
    @PutMapping("/{id}/classroom/remove")
    public ResponseEntity<StudentDto> removeStudentFromClassroom(@PathVariable String id) {
        Student student = studentService.removeStudentFromClassroom(id);
        return ResponseEntity.ok(converter.convert(student));
    }

    @Operation(summary = "Delete Student", description = "Delete Student by id", tags = { "Student" })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Student", description = "Get Student by id", tags = { "Student" })
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable String id) {
        return ResponseEntity.ok(converter.convert(studentService.findStudentById(id)));
    }

    @Operation(summary = "Get All Students", description = "Get All Students", tags = { "Student" })
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(converter.convert(studentService.findAllStudents()));
    }
}
