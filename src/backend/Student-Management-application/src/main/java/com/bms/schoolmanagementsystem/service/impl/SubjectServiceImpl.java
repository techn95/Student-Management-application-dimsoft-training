package com.bms.schoolmanagementsystem.service.impl;

import java.util.List;
import java.util.stream.*;

import org.springframework.stereotype.Service;

import com.bms.schoolmanagementsystem.dto.request.subject.CreateSubjectRequest;
import com.bms.schoolmanagementsystem.dto.request.subject.UpdateSubjectRequest;
import com.bms.schoolmanagementsystem.enums.StatusEnum;
import com.bms.schoolmanagementsystem.exception.subject.SubjectAlreadyExistException;
import com.bms.schoolmanagementsystem.exception.subject.SubjectNotFoundException;
import com.bms.schoolmanagementsystem.helper.BusinessMessage;
import com.bms.schoolmanagementsystem.helper.LogMessage;
import com.bms.schoolmanagementsystem.model.Subject;
import com.bms.schoolmanagementsystem.repository.SubjectRepository;
import com.bms.schoolmanagementsystem.service.SubjectService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    private SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Subject createSubject(CreateSubjectRequest request) {
        List<Subject> subjectFind = subjectRepository.findByIntitule(request.getIntitule());
        if (subjectFind.isEmpty()) {
            Subject subject = new Subject();

            subject.setIntitule(request.getIntitule());
            subject.setCoefficient(request.getCoefficient());
            Subject subjectSaved = subjectRepository.save(subject);
            log.info(LogMessage.Subject.SubjectCreated());
            return subjectSaved;
        }
        throw new SubjectAlreadyExistException(BusinessMessage.Subject.SUBJECT_ALREADY_EXISTS);
    }

    @Override
    public Subject updateSubject(String id, UpdateSubjectRequest request) {
        List<Subject> subjectsFind = subjectRepository.findByIntitule(request.getIntitule());
        if (subjectsFind.isEmpty()) {
            Subject existingSubject = findSubjectById(id);
            existingSubject.setIntitule(request.getIntitule());
            existingSubject.setCoefficient(request.getCoefficient());
            Subject updatedSubject = subjectRepository.save(existingSubject);
            log.info(LogMessage.Subject.SubjectAlreadyExists(id));
            return updatedSubject;
        }
        log.info(LogMessage.Subject.SubjectAlreadyExists(request.getIntitule()));
        throw new SubjectAlreadyExistException(BusinessMessage.Subject.SUBJECT_ALREADY_EXISTS);
    }

    @Override
    public Subject deleteSubject(String id) {
        Subject subject = findSubjectById(id);
        subject.setStatut(StatusEnum.DELETED);
        subjectRepository.save(subject);
        log.info(BusinessMessage.Subject.SUBJECT_DELETED_SUCCESSFULLY);
        return subject;
    }

    @Override
    public Subject findSubjectById(String id) {
        return subjectRepository.findById(id).orElseThrow(()->{
            log.error(LogMessage.Subject.SubjectNotFound(id));
            return new SubjectNotFoundException(BusinessMessage.Subject.SUBJECT_NOT_FOUND);
        });
    }

    @Override
    public List<Subject> findAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        if (subjects.isEmpty()) {
            log.error(LogMessage.Subject.SubjectListEmpty());
            throw new SubjectNotFoundException(BusinessMessage.Subject.SUBJECT_LIST_EMPTY);
        }
        log.info(LogMessage.Subject.SubjectListed());
        return subjects;
    }

    @Override
    public List<Subject> getAllActiveSubjects() {
        List<Subject> subjects = findAllSubjects();
        List<Subject> activeSubjects = subjects.stream().filter(subject -> subject.getStatut() == StatusEnum.ACTIVE).collect(Collectors.toList());
        if (activeSubjects.isEmpty()) {
            log.error(LogMessage.Subject.SubjectActiveListEmpty());
            throw new SubjectNotFoundException(BusinessMessage.Subject.SUBJECT_LIST_EMPTY);
        }
        log.info(LogMessage.Subject.SubjectActiveListed());
        return activeSubjects;
    }

    @Override
    public Subject updateSubjectStatus(String id, StatusEnum status) {
        Subject subject = findSubjectById(id);
        subject.setStatut(status);
        log.info(LogMessage.Subject.SubjectStatusUpdated(id));
        subjectRepository.save(subject);
        return subject;
    }

}
