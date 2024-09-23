package com.bms.schoolmanagementsystem.repository;

import com.bms.schoolmanagementsystem.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, String> {
    List<Address> findByCity(String city);
}