package com.bms.schoolmanagementsystem.controller;

import com.bms.schoolmanagementsystem.dto.TeacherDto;
import com.bms.schoolmanagementsystem.dto.converter.TeacherDtoConverter;
import com.bms.schoolmanagementsystem.dto.request.teacher.CreateTeacherRequest;
import com.bms.schoolmanagementsystem.dto.request.teacher.UpdateTeacherRequest;
import com.bms.schoolmanagementsystem.model.Teacher;
import com.bms.schoolmanagementsystem.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@Tag(name = "Teacher", description = "Teacher API")
@Slf4j
public class TeacherController {
    private final TeacherService teacherService;
    private final TeacherDtoConverter converter;

    public TeacherController(TeacherService teacherService, TeacherDtoConverter converter) {
        this.teacherService = teacherService;
        this.converter = converter;
    }

    @Operation(summary = "Create Teacher", description = "Create Teacher", tags = { "Teacher" })
    @PostMapping
    public ResponseEntity<TeacherDto> createTeacher(@Valid @RequestBody CreateTeacherRequest request) {
        Teacher teacher = teacherService.createTeacher(request);
        return new ResponseEntity<>(converter.convert(teacher), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Teacher", description = "Update Teacher by id", tags = { "Teacher" })
    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable String id,
            @Valid UpdateTeacherRequest request) {
        Teacher teacher = teacherService.updateTeacher(id, request);
        return ResponseEntity.ok(converter.convert(teacher));
    }

    @Operation(summary = "Delete Teacher", description = "Delete Teacher by id", tags = { "Teacher" })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable String id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Teacher", description = "Get Teacher by id", tags = { "Teacher" })
    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> findTeacherById(@PathVariable String id) {
        return ResponseEntity.ok(converter.convert(teacherService.findTeacherById(id)));
    }

    @Operation(summary = "Get All Teachers", description = "Get All Teachers", tags = { "Teacher" })
    @GetMapping
    public ResponseEntity<List<TeacherDto>> findAllTeachers() {
        return ResponseEntity.ok(converter.convert(teacherService.findAllTeachers()));
    }
}
