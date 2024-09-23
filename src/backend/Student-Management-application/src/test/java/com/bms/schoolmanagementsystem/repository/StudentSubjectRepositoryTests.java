package com.bms.schoolmanagementsystem.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.bms.schoolmanagementsystem.model.Student;
import com.bms.schoolmanagementsystem.model.StudentSubject;
import com.bms.schoolmanagementsystem.model.Subject;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
class StudentSubjectRepositoryTests {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    StudentSubjectRepository studentSubjectRepository;



    @Test
    void findStudentSubjectByStudent() {
        // given

        Student student = new Student();
        student.setFirstName("Vergez");
        student.setLastName("Kenfack");
        Student studentSave = studentRepository.save(student);

        Subject subject = new Subject();
        subject.setIntitule("Maths");
        Subject subjectSave = subjectRepository.save(subject);

        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setStudent(studentSave);
        studentSubject.setSubject(subjectSave);
        studentSubjectRepository.save(studentSubject);

        // when
        List<StudentSubject> studentSubjects = studentSubjectRepository.findByStudentId(studentSave.getId());
        // then
        assertEquals(studentSubject.getStudent().getFirstName(), studentSubjects.get(0).getStudent().getFirstName());
        assertEquals(studentSubject.getStudent().getLastName(), studentSubjects.get(0).getStudent().getLastName());
        assertEquals(studentSubject.getSubject().getIntitule(), studentSubjects.get(0).getSubject().getIntitule());
        assertEquals(1, studentSubjects.size());
    }

    @Test
    void findStudentSubjectBySubject() {
        // given


        Subject subject = new Subject();
        subject.setIntitule("Maths");
        Subject subjectSave = subjectRepository.save(subject);

        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setSubject(subjectSave);
        studentSubjectRepository.save(studentSubject);

        List<StudentSubject> studentSubjects = studentSubjectRepository.findBySubject(subjectSave);
        // then
        assertEquals(studentSubject.getSubject().getIntitule(), studentSubjects.get(0).getSubject().getIntitule());
        assertEquals(1, studentSubjects.size());
    }

}
