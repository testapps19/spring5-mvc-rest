package com.example.spring5mvcrest.controllers.v1;

import com.example.spring5mvcrest.api.v1.model.VendorDTO;
import com.example.spring5mvcrest.api.v1.model.VendorListDTO;
import com.example.spring5mvcrest.config.VendorResourceAssembler;
import com.example.spring5mvcrest.services.VendorService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {VendorController.class})
@Import({VendorResourceAssembler.class})
class VendorControllerTestIT {  //Using HATEOAS

    @MockBean //spring context
    VendorService vendorService;

    @Autowired //spring context
    MockMvc mockMvc;

    VendorDTO vendor1;
    VendorDTO vendor2;

    @BeforeEach
    public void setUp() throws Exception {
        vendor1 = VendorDTO.builder().name("Vendor 1").vendorUrl(VendorController.BASE_URL + "/1").build();
        vendor2 = VendorDTO.builder().name("Vendor 2").vendorUrl(VendorController.BASE_URL + "/2").build();
    }

    @Test
    public void getAllVendors() throws Exception{

        VendorListDTO vendorListDTO = new VendorListDTO(Arrays.asList(vendor1, vendor2));

        given(vendorService.getAllVendors()).willReturn(vendorListDTO);

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.vendorDTOList", hasSize(2)));
    }
}