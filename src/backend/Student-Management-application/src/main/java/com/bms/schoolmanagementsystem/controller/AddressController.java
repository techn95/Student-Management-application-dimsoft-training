package com.bms.schoolmanagementsystem.controller;

import com.bms.schoolmanagementsystem.dto.AddressDto;
import com.bms.schoolmanagementsystem.dto.converter.AddressDtoConverter;
import com.bms.schoolmanagementsystem.dto.request.address.CreateAddressRequest;
import com.bms.schoolmanagementsystem.dto.request.address.UpdateAddressRequest;
import com.bms.schoolmanagementsystem.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@Tag(name = "Address", description = "Address API")
public class AddressController {
    private final AddressService addressService;
    private final AddressDtoConverter converter;

    public AddressController(AddressService addressService, AddressDtoConverter converter) {
        this.addressService = addressService;
        this.converter = converter;
    }

    @Operation(summary = "Create Address", description = "Create Address", tags = { "Address" })
    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@RequestBody @Valid CreateAddressRequest request) {
        AddressDto addressDto = converter.convert(addressService.createAddress(request));
        return new ResponseEntity<>(addressDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Update Address", description = "Update Address by id", tags = { "Address" })
    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable String id,
            @Valid UpdateAddressRequest request) {
        AddressDto addressDto = converter.convert(addressService.updateAddress(id, request));
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete Address", description = "Delete Address by Id", tags = { "Address" })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Address", description = "Get Address by Id", tags = { "Address" })
    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> findAddressById(@PathVariable String id) {
        return ResponseEntity.ok(converter.convert(addressService.findAddressById(id)));
    }

    @Operation(summary = "Get All Addresses", description = "Get All Addresses", tags = { "Address" })
    @GetMapping
    public ResponseEntity<List<AddressDto>> findAllAddresses() {
        return ResponseEntity.ok(converter.convert(addressService.findAllAddresses()));
    }
}
