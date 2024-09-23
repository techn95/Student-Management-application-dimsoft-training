package com.bms.schoolmanagementsystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bms.schoolmanagementsystem.dto.request.studentsubject.CreateStudentSubjectRequest;
import com.bms.schoolmanagementsystem.exception.studentsubject.StudentSubjectAlreadyExistException;
import com.bms.schoolmanagementsystem.exception.studentsubject.StudentSubjectNotFoundException;
import com.bms.schoolmanagementsystem.helper.BusinessMessage;
import com.bms.schoolmanagementsystem.helper.LogMessage;
import com.bms.schoolmanagementsystem.model.StudentSubject;
import com.bms.schoolmanagementsystem.repository.StudentSubjectRepository;
import com.bms.schoolmanagementsystem.service.StudentSubjectService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentSubjectServiceImpl implements StudentSubjectService {

    private StudentSubjectRepository studentSubjectRepository;

    public StudentSubjectServiceImpl(StudentSubjectRepository studentSubjectRepository) {
        this.studentSubjectRepository = studentSubjectRepository;
    }

    @Override
    public StudentSubject createStudentSubject(CreateStudentSubjectRequest request) throws StudentSubjectAlreadyExistException {
        List<StudentSubject> listStudentSubjectBySubjects = studentSubjectRepository
                .findBySubject(request.getSubject());
        if (listStudentSubjectBySubjects.isEmpty()) {
            StudentSubject studentSubject = new StudentSubject();
            studentSubject.setYear(request.getYear());
            studentSubject.setNote(request.getNote());
            studentSubject.setStudent(request.getStudent());
            studentSubject.setSubject(request.getSubject());
            return studentSubjectRepository.save(studentSubject);
        }
        List<StudentSubject> listStudentSubjectBySubjectStudent = listStudentSubjectBySubjects.stream()
                .filter(studentSubject -> studentSubject.getStudent().getId().equals(request.getStudent().getId()))
                .filter(studentSubject -> studentSubject.getYear() == request.getYear())
                .collect(Collectors.toList());
        if (listStudentSubjectBySubjectStudent.isEmpty()) {
            StudentSubject studentSubject = new StudentSubject();
            studentSubject.setYear(request.getYear());
            studentSubject.setNote(request.getNote());
            studentSubject.setStudent(request.getStudent());
            studentSubject.setSubject(request.getSubject());
            return studentSubjectRepository.save(studentSubject);
        }
        
        throw new StudentSubjectAlreadyExistException(BusinessMessage.StudentSubject.STUDENT_SUBJECT_ALREADY_EXISTS);

    }

    @Override
    public void deleteStudentSubject(String id) {
        StudentSubject studentSubject = studentSubjectRepository.findById(id).orElseThrow(()->{
            log.error(LogMessage.StudentSubject.StudentSubjectNotFound(id));
            throw new StudentSubjectNotFoundException(BusinessMessage.StudentSubject.STUDENT_SUBJECT_NOT_FOUND);
        });
        studentSubjectRepository.delete(studentSubject);
        log.info(LogMessage.StudentSubject.StudentSubjectDeleted(id));
    }

    @Override
    public List<StudentSubject> findAllStudentSubjects() {
        List<StudentSubject> studentSubjects = studentSubjectRepository.findAll();
        if (studentSubjects.isEmpty()) {
            log.error(LogMessage.StudentSubject.StudentSubjectListEmpty());
            throw new StudentSubjectNotFoundException(BusinessMessage.StudentSubject.STUDENT_SUBJECT_LIST_EMPTY);
        }
        log.info(LogMessage.StudentSubject.StudentSubjectListed());
        return studentSubjects;
    }

}
