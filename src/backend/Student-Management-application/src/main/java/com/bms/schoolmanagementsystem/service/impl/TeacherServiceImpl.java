package com.bms.schoolmanagementsystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bms.schoolmanagementsystem.dto.request.teacher.CreateTeacherRequest;
import com.bms.schoolmanagementsystem.dto.request.teacher.UpdateTeacherRequest;
import com.bms.schoolmanagementsystem.exception.teacher.TeacherAlreadyExistException;
import com.bms.schoolmanagementsystem.exception.teacher.TeacherNotFoundException;
import com.bms.schoolmanagementsystem.helper.BusinessMessage;
import com.bms.schoolmanagementsystem.helper.DateHelper;
import com.bms.schoolmanagementsystem.helper.LogMessage;
import com.bms.schoolmanagementsystem.model.Teacher;
import com.bms.schoolmanagementsystem.repository.TeacherRepository;
import com.bms.schoolmanagementsystem.service.TeacherService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher createTeacher(CreateTeacherRequest request) {
        checkIfTeacherExists(request.getNationalId());

        Teacher teacher = new Teacher();
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setNationalId(request.getNationalId());
        teacher.setPhone(request.getPhone());
        teacher.setCreatedDate(DateHelper.getCurrentDate());
        teacher.setUpdatedDate(DateHelper.getCurrentDate());

        Teacher teacherSaved = teacherRepository.save(teacher);
        log.info(LogMessage.Teacher.TeacherCreated());
        return teacherSaved;
    }

    public Teacher updateTeacher(String id, UpdateTeacherRequest request) {
        Teacher teacher = findTeacherByTeacherId(id);

        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setPhone(request.getPhone());
        teacher.setUpdatedDate(DateHelper.getCurrentDate());

        Teacher teacherUpdated = teacherRepository.save(teacher);
        log.info(LogMessage.Teacher.TeacherUpdated(id));
        return teacherUpdated;
    }

    @Override
    public void deleteTeacher(String id) {
        Teacher teacher = findTeacherByTeacherId(id);

        teacherRepository.delete(teacher);
        log.info(LogMessage.Teacher.TeacherDeleted(id));
    }

    @Override
    public Teacher findTeacherById(String id) {
        Teacher teacher = findTeacherByTeacherId(id);

        log.info(LogMessage.Teacher.TeacherFound(id));
        return teacher;
    }

    @Override
    public List<Teacher> findAllTeachers() {
        List<Teacher> teacherList = teacherRepository.findAll();

        if (teacherList.isEmpty()) {
            log.error(LogMessage.Teacher.TeacherListEmpty());
            throw new TeacherNotFoundException(BusinessMessage.Teacher.TEACHER_LIST_EMPTY);
        }

        log.info(LogMessage.Teacher.TeacherListed());
        return teacherList;
    }

    private void checkIfTeacherExists(String nationalId) {
        if (teacherRepository.existsByNationalId(nationalId)) {
            log.error(LogMessage.Teacher.TeacherAlreadyExists(nationalId));
            throw new TeacherAlreadyExistException(BusinessMessage.Teacher.TEACHER_ALREADY_EXISTS);
        }
    }

    @Override
    public Teacher findTeacherByTeacherId(String id) {
        return teacherRepository.findById(id).orElseThrow(() -> {
            log.error(LogMessage.Teacher.TeacherNotFound(id));
            return new TeacherNotFoundException(BusinessMessage.Teacher.TEACHER_NOT_FOUND);
        });
    }
}
