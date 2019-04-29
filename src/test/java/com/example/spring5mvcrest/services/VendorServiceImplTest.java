package com.example.spring5mvcrest.services;

import com.example.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.example.spring5mvcrest.api.v1.model.VendorDTO;
import com.example.spring5mvcrest.api.v1.model.VendorListDTO;
import com.example.spring5mvcrest.domain.Vendor;
import com.example.spring5mvcrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class VendorServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "Vendor 1";
    public static final String URI = "/api/v1/vendors/1";
    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    VendorDTO vendorDTO;
    Vendor vendor;

    @BeforeEach
    void setUp() {

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);

        vendorDTO = VendorDTO.builder().id(ID).name(NAME).build();

        vendor = Vendor.builder().id(ID).name(vendorDTO.getName()).build();

    }


    @Test
    public void getAllVendors() throws Exception{

        //given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());

        when(vendorRepository.findAll()).thenReturn(vendors);

        //when
        VendorListDTO vendorDTOS = vendorService.getAllVendors();

        //then
        assertEquals(2, vendorDTOS.getVendors().size());
    }

    @Test
    public void getVendorByName() throws Exception{

/*        //given

        when(vendorRepository.findByName(anyString())).thenReturn(vendor);

        //when
        VendorDTO vendorDTO = vendorService.getVendorByName(NAME);

        //then
        assertEquals(NAME, vendorDTO.getName());*/

        //mockito BDD syntax
        given(vendorRepository.findByName(anyString())).willReturn(vendor);

        //when
        VendorDTO vendorDTO = vendorService.getVendorByName(NAME);

        //then
        then(vendorRepository).should(times(1)).findByName(anyString());

        assertThat(vendorDTO.getName(), is(equalTo(NAME)));

    }

    @Test
    public void createNewVendor() throws Exception {
        //given

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        //when
        VendorDTO savedDTO = vendorService.createNewVendor(vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), savedDTO.getName());
        assertEquals(URI, savedDTO.getVendorUrl());
    }

    @Test
    public void saveVendorByDTO() throws Exception {
        //given

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        //when
        VendorDTO vendorDTO1 = vendorService.saveVendorByDTO(ID, vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), vendorDTO1.getName());
        assertEquals(URI, vendorDTO1.getVendorUrl());

    }

    @Test
    public void patchVendor() throws Exception{

        Vendor vendor1 = Vendor.builder().id(ID).name(NAME).build();

        when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(vendor1));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        //when
        VendorDTO savedDTO = vendorService.patchVendor(ID, vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), savedDTO.getName());
    }

    @Test
    public void deleteVendor() throws Exception{

        vendorRepository.deleteById(ID);

        verify(vendorRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void getVendorByNameNotFound() throws Exception {
        //given
        //mockito BBD syntax since mockito 1.10.0
        given(vendorRepository.findByName(anyString())).willReturn(null);

        assertThrows(ResourceNotFoundException.class,
                ()->{

            //when
            VendorDTO vendorDTO = vendorService.getVendorByName(NAME);

            //then
            then(vendorRepository).should(times(1)).findByName(anyString());

        });

    }
}