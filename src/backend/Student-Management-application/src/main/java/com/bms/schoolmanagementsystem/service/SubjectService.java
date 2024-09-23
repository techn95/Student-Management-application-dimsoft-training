package com.bms.schoolmanagementsystem.service;

import java.util.List;

import com.bms.schoolmanagementsystem.dto.request.subject.CreateSubjectRequest;
import com.bms.schoolmanagementsystem.dto.request.subject.UpdateSubjectRequest;
import com.bms.schoolmanagementsystem.enums.StatusEnum;
import com.bms.schoolmanagementsystem.model.Subject;

public interface SubjectService {
    Subject createSubject(CreateSubjectRequest request);

    Subject updateSubject(String id, UpdateSubjectRequest request);

    Subject deleteSubject(String id);

    Subject findSubjectById(String id);

    List<Subject> findAllSubjects();

    List<Subject> getAllActiveSubjects();

    Subject updateSubjectStatus(String id, StatusEnum status);
}
