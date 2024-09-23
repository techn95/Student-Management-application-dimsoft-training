package com.bms.schoolmanagementsystem.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.bms.schoolmanagementsystem.model.Subject;

@SpringBootTest
@ActiveProfiles("test")
class SubjectRepositoryTests {

    @Autowired
    SubjectRepository subjectRepository;
    
    @BeforeEach
    void setup(){
        subjectRepository.deleteAll();
    }

    @Test
    void findSubjectByIntitule(){
        //given
        Subject subject = new Subject();

        subject.setCoefficient(6);
        subject.setIntitule("mathematiques");

        Subject subject1 = new Subject();

        subject1.setCoefficient(4);
        subject1.setIntitule("physiques");

        subjectRepository.save(subject);
        subjectRepository.save(subject1);
        List<Subject> results = new ArrayList<Subject>();
        results.add(subject);

        String intitule = "mathematiques";

        // when
        List<Subject> intitules = subjectRepository.findByIntitule(intitule);

        // then
        assertEquals(subject.getIntitule(), intitules.get(0).getIntitule());
    }

    @Test
    void findSubjectByCoeficient(){
        //given
        Subject subject = new Subject();

        subject.setCoefficient(4);
        subject.setIntitule("physiques");

        subjectRepository.save(subject);
        List<Subject> results = new ArrayList<Subject>();
        results.add(subject);
        int coef = 4;

        // when
        List<Subject> intitules = subjectRepository.findByCoefficient(coef);

        // then
        assertEquals(subject.getIntitule(), intitules.get(0).getIntitule());
        assertEquals(subject.getCoefficient(), intitules.get(0).getCoefficient());
    }

}
