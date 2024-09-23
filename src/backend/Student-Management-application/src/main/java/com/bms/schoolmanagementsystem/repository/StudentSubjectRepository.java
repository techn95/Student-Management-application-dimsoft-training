package com.bms.schoolmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bms.schoolmanagementsystem.model.StudentSubject;
import com.bms.schoolmanagementsystem.model.Subject;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, String> {

    List<StudentSubject> findByStudentId(String studentId);
    List<StudentSubject> findBySubject(Subject subject);

    
}