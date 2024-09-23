package com.bms.schoolmanagementsystem.service;

import java.util.List;

import com.bms.schoolmanagementsystem.dto.request.student.CreateStudentRequest;
import com.bms.schoolmanagementsystem.dto.request.student.UpdateStudentRequest;
import com.bms.schoolmanagementsystem.model.Student;

public interface StudentService {
    public Student createStudent(CreateStudentRequest request);

    public Student updateStudent(String id, UpdateStudentRequest request);

    public Student addStudentToClassroom(String id, String classroomId);

    public Student removeStudentFromClassroom(String id);

    public void deleteStudent(String id);

    public Student findStudentById(String id);

    public List<Student> findAllStudents();

    public Student findStudentByStudentId(String id);
}
