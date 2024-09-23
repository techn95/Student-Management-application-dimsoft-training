package com.bms.schoolmanagementsystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bms.schoolmanagementsystem.dto.request.classroom.CreateClassroomRequest;
import com.bms.schoolmanagementsystem.dto.request.classroom.UpdateClassroomRequest;
import com.bms.schoolmanagementsystem.exception.classroom.ClassroomNotFoundException;
import com.bms.schoolmanagementsystem.helper.BusinessMessage;
import com.bms.schoolmanagementsystem.helper.GenerateClassroomName;
import com.bms.schoolmanagementsystem.helper.LogMessage;
import com.bms.schoolmanagementsystem.model.Classroom;
import com.bms.schoolmanagementsystem.repository.ClassroomRepository;
import com.bms.schoolmanagementsystem.service.ClassroomService;
import com.bms.schoolmanagementsystem.service.TeacherService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final TeacherService teacherService;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository,
            TeacherService teacherService) {
        this.classroomRepository = classroomRepository;
        this.teacherService = teacherService;
    }

    @Override
    public Classroom createClassroom(CreateClassroomRequest request) {
        Classroom classroom = new Classroom();
        classroom.setName(GenerateClassroomName.generate());
        classroom.setDescription(request.getDescription());
        classroom.setTeacher(teacherService.findTeacherByTeacherId(request.getTeacherId()));

        Classroom classroomSaved = classroomRepository.save(classroom);
        log.info(LogMessage.Classroom.ClassroomCreated());
        return classroomSaved;
    }

    @Override
    public Classroom updateClassroom(String id, UpdateClassroomRequest request) {
        Classroom classroom = findClassroomByClassroomId(id);

        classroom.setDescription(request.getDescription());
        classroom.setTeacher(teacherService.findTeacherByTeacherId(request.getTeacherId()));

        Classroom classroomUpdated = classroomRepository.save(classroom);
        log.info(LogMessage.Classroom.ClassroomUpdated(id));
        return classroomUpdated;
    }

    @Override
    public void deleteClassroom(String id) {
        Classroom classroom = findClassroomByClassroomId(id);

        classroomRepository.delete(classroom);
        log.info(LogMessage.Classroom.ClassroomDeleted(id));
    }

    @Override
    public Classroom findClassroomById(String id) {
        Classroom classroom = findClassroomByClassroomId(id);

        log.info(LogMessage.Classroom.ClassroomFound(id));
        return classroom;
    }

    @Override
    public List<Classroom> findAllClassrooms() {
        List<Classroom> classroomList = classroomRepository.findAll();

        if (classroomList.isEmpty()) {
            log.error(LogMessage.Classroom.ClassroomListEmpty());
            throw new ClassroomNotFoundException(BusinessMessage.Classroom.CLASSROOM_LIST_EMPTY);
        }

        log.info(LogMessage.Classroom.ClassroomListed());
        return classroomList;
    }

    @Override
    public Classroom findClassroomByClassroomId(String id) {
        return classroomRepository.findById(id).orElseThrow(() -> {
            log.error(LogMessage.Classroom.ClassroomNotFound(id));
            throw new ClassroomNotFoundException(BusinessMessage.Classroom.CLASSROOM_NOT_FOUND);
        });
    }
}
