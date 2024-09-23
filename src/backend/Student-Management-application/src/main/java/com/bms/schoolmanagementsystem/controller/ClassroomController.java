package com.bms.schoolmanagementsystem.controller;

import com.bms.schoolmanagementsystem.dto.ClassroomDto;
import com.bms.schoolmanagementsystem.dto.converter.ClassroomDtoConverter;
import com.bms.schoolmanagementsystem.dto.request.classroom.CreateClassroomRequest;
import com.bms.schoolmanagementsystem.dto.request.classroom.UpdateClassroomRequest;
import com.bms.schoolmanagementsystem.model.Classroom;
import com.bms.schoolmanagementsystem.service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/classrooms")
@Tag(name = "Classroo", description = "Classroom API")
public class ClassroomController {
    private final ClassroomService classroomService;
    private final ClassroomDtoConverter converter;

    public ClassroomController(ClassroomService classroomService, ClassroomDtoConverter converter) {
        this.classroomService = classroomService;
        this.converter = converter;
    }

    @Operation(summary = "Create a classroom", description = "Create a classroom", tags = { "Classroom" })
    @PostMapping
    public ResponseEntity<ClassroomDto> createClassroom(@Valid CreateClassroomRequest request) {
        Classroom classroom = classroomService.createClassroom(request);
        return new ResponseEntity<>(converter.convert(classroom), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a classroom", description = "Update a classroom by id", tags = { "Classroom" })
    @PutMapping("/{id}")
    public ResponseEntity<ClassroomDto> updateClassroom(@PathVariable String id,
            @Valid UpdateClassroomRequest request) {
        Classroom classroom = classroomService.updateClassroom(id, request);
        return ResponseEntity.ok(converter.convert(classroom));
    }

    @Operation(summary = "Delete a classroom", description = "Delete a classroom by id", tags = { "Classroom" })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable String id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get a classroom", description = "Get a classroom by id", tags = { "Classroom" })
    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDto> findClassroomById(@PathVariable String id) {
        return ResponseEntity.ok(converter.convert(classroomService.findClassroomById(id)));
    }

    @Operation(summary = "Get all classrooms", description = "Get all classrooms", tags = { "Classroom" })
    @GetMapping
    public ResponseEntity<List<ClassroomDto>> findAllClassrooms() {
        return ResponseEntity.ok(converter.convert(classroomService.findAllClassrooms()));
    }
}
