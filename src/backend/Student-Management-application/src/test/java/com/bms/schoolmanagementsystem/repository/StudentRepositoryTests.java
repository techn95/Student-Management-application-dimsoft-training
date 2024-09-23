package com.bms.schoolmanagementsystem.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class StudentRepositoryTests {

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void setup() {
        studentRepository.deleteAll();
    }

    @Test
    void testStudentShouldNotExistsByMotherPhone() {
        // given
        String phone = "213P";

        // when
        boolean response = studentRepository.existsByMotherPhone(phone);

        // then
        assertFalse(response);
    }

}
