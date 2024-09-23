package com.bms.schoolmanagementsystem.service;

import java.util.List;

import com.bms.schoolmanagementsystem.dto.request.teacher.CreateTeacherRequest;
import com.bms.schoolmanagementsystem.dto.request.teacher.UpdateTeacherRequest;
import com.bms.schoolmanagementsystem.model.Teacher;

public interface TeacherService {
    public Teacher createTeacher(CreateTeacherRequest request);

    public Teacher updateTeacher(String id, UpdateTeacherRequest request);

    public void deleteTeacher(String id);

    public Teacher findTeacherById(String id);

    public List<Teacher> findAllTeachers();

    public Teacher findTeacherByTeacherId(String id);
}
