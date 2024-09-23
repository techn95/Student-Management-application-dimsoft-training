package com.bms.schoolmanagementsystem.repository;

import com.bms.schoolmanagementsystem.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, String> {
    List<Classroom> findByName(String name);
}