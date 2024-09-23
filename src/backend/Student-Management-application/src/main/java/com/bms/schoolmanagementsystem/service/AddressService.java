package com.bms.schoolmanagementsystem.service;

import java.util.List;

import com.bms.schoolmanagementsystem.dto.request.address.CreateAddressRequest;
import com.bms.schoolmanagementsystem.dto.request.address.UpdateAddressRequest;
import com.bms.schoolmanagementsystem.model.Address;

public interface AddressService {
    public Address createAddress(CreateAddressRequest request);

    public Address updateAddress(String id, UpdateAddressRequest request);

    public void deleteAddress(String id);

    public Address findAddressById(String id);

    public List<Address> findAllAddresses();

    public Address findAddressByAddressId(String id);
}
