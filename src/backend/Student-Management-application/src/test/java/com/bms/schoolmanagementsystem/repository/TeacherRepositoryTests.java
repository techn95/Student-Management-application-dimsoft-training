package com.bms.schoolmanagementsystem.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TeacherRepositoryTests {
    @Autowired
    TeacherRepository teacherRepository;

    @BeforeEach
    void setup() {
        teacherRepository.deleteAll();
    }

    @Test
    void testTeacherShouldNotExistsNationalId() {
        // given
        String nationalId = "AHNKJZ213P";

        // when
        boolean response = teacherRepository.existsByNationalId(nationalId);

        // then
        assertFalse(response);
    }
}
