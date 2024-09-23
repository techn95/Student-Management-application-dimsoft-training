package com.bms.schoolmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bms.schoolmanagementsystem.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, String> {

    List<Subject> findByIntitule(String intitule);
    List<Subject> findByCoefficient(int coefficient);

}
