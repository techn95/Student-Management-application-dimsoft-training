package com.bms.schoolmanagementsystem.service.units_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bms.schoolmanagementsystem.dto.request.studentsubject.CreateStudentSubjectRequest;
import com.bms.schoolmanagementsystem.exception.studentsubject.StudentSubjectAlreadyExistException;
import com.bms.schoolmanagementsystem.exception.studentsubject.StudentSubjectNotFoundException;
import com.bms.schoolmanagementsystem.helper.BusinessMessage;
import com.bms.schoolmanagementsystem.model.Student;
import com.bms.schoolmanagementsystem.model.StudentSubject;
import com.bms.schoolmanagementsystem.model.Subject;
import com.bms.schoolmanagementsystem.repository.StudentSubjectRepository;
import com.bms.schoolmanagementsystem.service.StudentSubjectService;
import com.bms.schoolmanagementsystem.service.impl.StudentSubjectServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StudentSubjectServiceTests {

    @Mock
    StudentSubjectRepository studentSubjectRepository;

    StudentSubjectService studentSubjectService;

    @BeforeEach
    void setup(){
        studentSubjectService = new StudentSubjectServiceImpl(studentSubjectRepository);
    }

    @Test
    void candAddStudentSubjectWithSuccess() throws StudentSubjectAlreadyExistException{
        // Given
        Student student = new Student();
        student.setFirstName("vergez");
        student.setLastName("vergez");

        Subject subject = new Subject();
        subject.setIntitule("Maths");
        subject.setCoefficient(6);
        
        CreateStudentSubjectRequest createStudentSubjectRequest = new CreateStudentSubjectRequest();
        createStudentSubjectRequest.setStudent(student);
        createStudentSubjectRequest.setSubject(subject);
        createStudentSubjectRequest.setNote(15);
        createStudentSubjectRequest.setYear(2020);
        // When
        studentSubjectService.createStudentSubject(createStudentSubjectRequest);
        // Then
        ArgumentCaptor<StudentSubject> studentSubjArgumentCaptor = ArgumentCaptor.forClass(StudentSubject.class);
        verify(studentSubjectRepository, times(1)).save(studentSubjArgumentCaptor.capture());
        assertEquals(15, studentSubjArgumentCaptor.getValue().getNote());
        assertEquals(2020, studentSubjArgumentCaptor.getValue().getYear());
        assertEquals("vergez", studentSubjArgumentCaptor.getValue().getStudent().getFirstName());
        assertEquals("vergez", studentSubjArgumentCaptor.getValue().getStudent().getLastName());
        assertEquals("Maths", studentSubjArgumentCaptor.getValue().getSubject().getIntitule());
        assertEquals(6, studentSubjArgumentCaptor.getValue().getSubject().getCoefficient());
        assertTrue(studentSubjArgumentCaptor.getValue().getSubject() instanceof Subject);
        assertTrue(studentSubjArgumentCaptor.getValue().getStudent() instanceof Student);
    }

    @Test
    void candAddStudentSubjectWithThrowStudentSubjectAlreadyExist() throws StudentSubjectAlreadyExistException{
        //Given
        Student student = new Student();
        student.setId("vers");
        student.setFirstName("vergez");
        student.setLastName("vergez");

        Subject subject = new Subject();
        subject.setId("maths");
        subject.setIntitule("Maths");
        subject.setCoefficient(6);

        CreateStudentSubjectRequest createStudentSubjectRequest = new CreateStudentSubjectRequest();
        createStudentSubjectRequest.setStudent(student);
        createStudentSubjectRequest.setSubject(subject);
        createStudentSubjectRequest.setNote(15);
        createStudentSubjectRequest.setYear(2020);
        
        StudentSubject studentSubjectRequest = new StudentSubject();
        studentSubjectRequest.setId("null");
        studentSubjectRequest.setStudent(student);
        studentSubjectRequest.setSubject(subject);
        studentSubjectRequest.setNote(15);
        studentSubjectRequest.setYear(2020);

        List<StudentSubject> mocksStudentSubjectList = new ArrayList<StudentSubject>();
        mocksStudentSubjectList.add(studentSubjectRequest);
        // When
        when(studentSubjectRepository.findBySubject(any())).thenReturn(mocksStudentSubjectList);
       String msg = assertThrows(StudentSubjectAlreadyExistException.class, ()->{
        studentSubjectService.createStudentSubject(createStudentSubjectRequest);
       }).getMessage();
        // Then
        ArgumentCaptor<Subject> subjArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
        verify(studentSubjectRepository, never()).save(any());
        verify(studentSubjectRepository, times(1)).findBySubject(subjArgumentCaptor.capture());
        assertEquals(BusinessMessage.StudentSubject.STUDENT_SUBJECT_ALREADY_EXISTS, msg);
        assertEquals("Maths", subjArgumentCaptor.getValue().getIntitule());
        assertEquals(6, subjArgumentCaptor.getValue().getCoefficient());
        assertTrue(subjArgumentCaptor.getValue() instanceof Subject);
        assertEquals("maths", subjArgumentCaptor.getValue().getId());
    }

    @Test
    void candDeleteStudentSubjectWithSuccess(){
        //Given
        Student student = new Student();
        student.setId("vers");
        student.setFirstName("vergez");
        student.setLastName("vergez");

        Subject subject = new Subject();
        subject.setId("maths");
        subject.setIntitule("Maths");
        subject.setCoefficient(6);
        
        StudentSubject studentSubjectRequest = new StudentSubject();
        studentSubjectRequest.setId("null");
        studentSubjectRequest.setStudent(student);
        studentSubjectRequest.setSubject(subject);
        studentSubjectRequest.setNote(15);
        studentSubjectRequest.setYear(2020);
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<StudentSubject> studentSubjectCaptor = ArgumentCaptor.forClass(StudentSubject.class);
        //when
        when(studentSubjectRepository.findById(anyString())).thenReturn(Optional.of(studentSubjectRequest));
        studentSubjectService.deleteStudentSubject("null");
        //then
        verify(studentSubjectRepository, times(1)).findById(idCaptor.capture());
        verify(studentSubjectRepository, times(1)).delete(studentSubjectCaptor.capture());
        assertEquals("null", idCaptor.getValue());
        assertEquals("maths", studentSubjectCaptor.getValue().getSubject().getId());
        assertEquals("Maths", studentSubjectCaptor.getValue().getSubject().getIntitule());
        assertEquals(6, studentSubjectCaptor.getValue().getSubject().getCoefficient());
        assertEquals("vergez", studentSubjectCaptor.getValue().getStudent().getFirstName());
        assertEquals("vergez", studentSubjectCaptor.getValue().getStudent().getLastName());
        assertEquals(2020, studentSubjectCaptor.getValue().getYear());
        assertEquals(15, studentSubjectCaptor.getValue().getNote());
        
    }

    @Test
    void candDeleteStudentSubjectWithStudentSubjectNotFound(){
        //when
        String msg = assertThrows(StudentSubjectNotFoundException.class, ()->{
            studentSubjectService.deleteStudentSubject("null");
        }).getMessage();
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        //then
        verify(studentSubjectRepository, times(1)).findById(idCaptor.capture());
        verify(studentSubjectRepository, never()).delete(any());
        assertEquals("null", idCaptor.getValue());
        assertEquals(BusinessMessage.StudentSubject.STUDENT_SUBJECT_NOT_FOUND, msg);
    }

    @Test
    void findAllStudentSubjectsWithSuccess(){
        //given
        Student student = new Student();
        student.setId("vers");
        student.setFirstName("vergez");
        student.setLastName("vergez");

        Subject subject = new Subject();
        subject.setId("maths");
        subject.setIntitule("Maths");
        subject.setCoefficient(6);
        
        StudentSubject studentSubjectRequest = new StudentSubject();
        studentSubjectRequest.setId("null");
        studentSubjectRequest.setStudent(student);
        studentSubjectRequest.setSubject(subject);
        studentSubjectRequest.setNote(15);
        studentSubjectRequest.setYear(2020);
        List<StudentSubject> mocksStudentSubjectList = new ArrayList<StudentSubject>();
        mocksStudentSubjectList.add(studentSubjectRequest);
        //when
        when(studentSubjectRepository.findAll()).thenReturn(mocksStudentSubjectList);
        List<StudentSubject> studentSubjectList = studentSubjectService.findAllStudentSubjects();
        //then
        verify(studentSubjectRepository, times(1)).findAll();
        assertEquals(1, studentSubjectList.size());
        assertEquals("null", studentSubjectList.get(0).getId());
        assertEquals("maths", studentSubjectList.get(0).getSubject().getId());
        assertEquals("Maths", studentSubjectList.get(0).getSubject().getIntitule());
        assertEquals(6, studentSubjectList.get(0).getSubject().getCoefficient());
        assertEquals("vergez", studentSubjectList.get(0).getStudent().getFirstName());
        assertEquals("vergez", studentSubjectList.get(0).getStudent().getLastName());
        assertEquals(2020, studentSubjectList.get(0).getYear());
        assertEquals(15, studentSubjectList.get(0).getNote());
    }

    @Test
    void findAllStudentSubjectsWithThrowStudentSubjectNotFound(){
        // when
        String msg = assertThrows(StudentSubjectNotFoundException.class, ()->{
            studentSubjectService.findAllStudentSubjects();
        }).getMessage();
        // then
        verify(studentSubjectRepository, times(1)).findAll();
        assertEquals(BusinessMessage.StudentSubject.STUDENT_SUBJECT_LIST_EMPTY, msg);
    } 

}
