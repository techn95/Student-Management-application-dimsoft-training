package com.bms.schoolmanagementsystem.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.bms.schoolmanagementsystem.model.Address;

@SpringBootTest
@ActiveProfiles("test")
class AddressRepositoryTests {
    @Autowired
    AddressRepository addressRepository;

    @BeforeEach
    void setup() {
        addressRepository.deleteAll();
    }

    @Test
    void testShouldGetAnEmptyListWhenFindByCity() {
        // given
        String city = "Dschang";

        // when
        List<Address> addresses = addressRepository.findByCity(city);

        // then
        assertNotNull(addresses);
        assertTrue(addresses.isEmpty());
    }
}
