package com.bms.schoolmanagementsystem.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.bms.schoolmanagementsystem.model.Classroom;

@SpringBootTest
@ActiveProfiles("test")
class ClassroomRepositoryTests {
    @Autowired
    ClassroomRepository classroomRepository;

    @BeforeEach
    void setup() {
        classroomRepository.deleteAll();
    }

    @Test
    void testShouldGetAnEmptyListWhenFindByCity() {
        // given
        String name = "Terminal";

        // when
        List<Classroom> classrooms = classroomRepository.findByName(name);

        // then
        assertNotNull(classrooms);
        assertTrue(classrooms.isEmpty());
    }
}
