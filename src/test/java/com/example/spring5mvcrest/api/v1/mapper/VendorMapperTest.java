package com.example.spring5mvcrest.api.v1.mapper;

import com.example.spring5mvcrest.api.v1.model.VendorDTO;
import com.example.spring5mvcrest.domain.Vendor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendorMapperTest {


    public static final long ID = 1L;
    public static final String NAME = "Vendor 1";
    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() throws Exception{

        //given
        Vendor vendor = Vendor.builder().id(ID).name(NAME).build();

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        //then
        assertEquals(Long.valueOf(ID), vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());


    }
}