package com.bms.schoolmanagementsystem.service;

import java.util.List;

import com.bms.schoolmanagementsystem.dto.request.classroom.CreateClassroomRequest;
import com.bms.schoolmanagementsystem.dto.request.classroom.UpdateClassroomRequest;
import com.bms.schoolmanagementsystem.model.Classroom;

public interface ClassroomService {
    public Classroom createClassroom(CreateClassroomRequest request);

    public Classroom updateClassroom(String id, UpdateClassroomRequest request);

    public void deleteClassroom(String id);

    public Classroom findClassroomById(String id);

    public List<Classroom> findAllClassrooms();

    public Classroom findClassroomByClassroomId(String id);
}
