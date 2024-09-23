package com.bms.schoolmanagementsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bms.schoolmanagementsystem.dto.SubjectDto;
import com.bms.schoolmanagementsystem.dto.converter.SubjectDtoConverter;
import com.bms.schoolmanagementsystem.dto.request.subject.CreateSubjectRequest;
import com.bms.schoolmanagementsystem.dto.request.subject.UpdateSubjectRequest;
import com.bms.schoolmanagementsystem.enums.StatusEnum;
import com.bms.schoolmanagementsystem.service.SubjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/subjects")
@Tag(name = "Subject", description = "Subject API")
public class SubjectController {

    private final SubjectService subjectService;
    private final SubjectDtoConverter subjectDtoCon;

    public SubjectController(SubjectService subjectService, SubjectDtoConverter subjectDtoCon) {
        this.subjectService = subjectService;
        this.subjectDtoCon = subjectDtoCon;
    }

    @Operation(summary = "Create Subject", description = "Create a Subject", tags = {"Subject"})
    @PostMapping
    public ResponseEntity<SubjectDto> createSubject(@Valid @RequestBody CreateSubjectRequest createSubjectRequest) {
        SubjectDto subjectDto = subjectDtoCon.convert(subjectService.createSubject(createSubjectRequest));
        return new ResponseEntity<SubjectDto>(subjectDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Update Subject", description = "Update subject by Id", tags = {"Subject"})
    @PutMapping("/{id}")
    public ResponseEntity<SubjectDto> updateSubject(@PathVariable String id,
            @RequestBody @Valid UpdateSubjectRequest updateSubjectRequest) {

        SubjectDto updateSubject = subjectDtoCon.convert(subjectService.updateSubject(id, updateSubjectRequest));

        return new ResponseEntity<SubjectDto>(updateSubject, HttpStatus.OK);
    }

    @Operation(summary = "Delete Subject", description = "Delete a Subject by id", tags = {"Subject"})
    @DeleteMapping("/{id}")
    public ResponseEntity<SubjectDto> deleteSubject(@PathVariable String id){
        SubjectDto subjectDto = subjectDtoCon.convert(subjectService.deleteSubject(id));
        return new ResponseEntity<SubjectDto>(subjectDto, HttpStatus.OK);
    }

    @Operation(summary = "Get Subject By Id", description = "Get a subject by id", tags = {"Subject"})
    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable String id) {
        SubjectDto subjectDto = subjectDtoCon.convert(subjectService.findSubjectById(id));

        return new ResponseEntity<SubjectDto>(subjectDto, HttpStatus.OK); 
    }

    @Operation(summary = "Get All Active Subject", description = "Get All Active Subjects", tags = {"Subject"})
    @GetMapping("/getAllActiveSubjects")
    public ResponseEntity<List<SubjectDto>> getAllActiveSubjects() {
        return new ResponseEntity<List<SubjectDto>>(subjectDtoCon.convert(subjectService.getAllActiveSubjects()), HttpStatus.OK);
    }

    @Operation(summary = "Update Subject Status", description = "Update Subject Status by id", tags = {"Subject"})
    @PutMapping("updateStatus/{id}")
    public ResponseEntity<SubjectDto> updateSubjectStatus(@PathVariable String id, @RequestBody StatusEnum statusEnum) {
        
        SubjectDto subjectDto = subjectDtoCon.convert(subjectService.updateSubjectStatus(id, statusEnum));
        
        return new ResponseEntity<SubjectDto>(subjectDto, HttpStatus.OK);
    }
    

    @Operation(summary = "Get All Subject", description = "Get all subjects", tags = {"Subject"})
    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAllSubject() {
        return ResponseEntity.ok(subjectDtoCon.convert(subjectService.findAllSubjects()));
    }
    

}
