package com.bms.schoolmanagementsystem.service;

import java.util.List;

import com.bms.schoolmanagementsystem.dto.request.studentsubject.CreateStudentSubjectRequest;
import com.bms.schoolmanagementsystem.exception.studentsubject.StudentSubjectAlreadyExistException;
import com.bms.schoolmanagementsystem.model.StudentSubject;

public interface StudentSubjectService {
    StudentSubject createStudentSubject(CreateStudentSubjectRequest request) throws StudentSubjectAlreadyExistException;
    void deleteStudentSubject(String id);
    List<StudentSubject> findAllStudentSubjects();
}
