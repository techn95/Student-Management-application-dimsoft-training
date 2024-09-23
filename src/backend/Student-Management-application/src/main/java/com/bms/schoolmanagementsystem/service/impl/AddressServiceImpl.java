package com.bms.schoolmanagementsystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bms.schoolmanagementsystem.dto.request.address.CreateAddressRequest;
import com.bms.schoolmanagementsystem.dto.request.address.UpdateAddressRequest;
import com.bms.schoolmanagementsystem.exception.address.AddressNotFoundException;
import com.bms.schoolmanagementsystem.helper.BusinessMessage;
import com.bms.schoolmanagementsystem.helper.LogMessage;
import com.bms.schoolmanagementsystem.model.Address;
import com.bms.schoolmanagementsystem.repository.AddressRepository;
import com.bms.schoolmanagementsystem.service.AddressService;
import com.bms.schoolmanagementsystem.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final StudentService studentService;

    public AddressServiceImpl(AddressRepository addressRepository,
            StudentService studentService) {
        this.addressRepository = addressRepository;
        this.studentService = studentService;
    }

    @Override
    public Address createAddress(CreateAddressRequest request) {
        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());
        address.setStudent(studentService.findStudentByStudentId(request.getStudentId()));

        Address addressSaved = addressRepository.save(address);
        log.info(LogMessage.Address.AddressCreated());
        return addressSaved;
    }

    @Override
    public Address updateAddress(String id, UpdateAddressRequest request) {
        Address address = findAddressByAddressId(id);

        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());

        Address addressUpdated = addressRepository.save(address);
        log.info(LogMessage.Address.AddressUpdated(id));
        return addressUpdated;
    }

    @Override
    public void deleteAddress(String id) {
        Address address = findAddressByAddressId(id);

        addressRepository.delete(address);
        log.info(LogMessage.Address.AddressDeleted(id));
    }

    public Address findAddressById(String id) {
        Address address = findAddressByAddressId(id);

        log.info(LogMessage.Address.AddressFound(id));
        return address;
    }

    @Override
    public List<Address> findAllAddresses() {
        List<Address> addressList = addressRepository.findAll();

        if (addressList.isEmpty()) {
            log.error(LogMessage.Address.AddressListEmpty());
            throw new AddressNotFoundException(BusinessMessage.Address.ADDRESS_LIST_EMPTY);
        }

        log.info(LogMessage.Address.AddressListed());
        return addressList;
    }

    @Override
    public Address findAddressByAddressId(String id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(LogMessage.Address.AddressNotFound(id));
                    throw new AddressNotFoundException(LogMessage.Address.AddressNotFound(id));
                });
    }
}
