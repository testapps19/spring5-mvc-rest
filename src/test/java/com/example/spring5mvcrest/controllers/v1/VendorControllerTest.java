package com.example.spring5mvcrest.controllers.v1;

import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.api.v1.model.VendorDTO;
import com.example.spring5mvcrest.api.v1.model.VendorListDTO;
import com.example.spring5mvcrest.config.VendorResourceAssembler;
import com.example.spring5mvcrest.exceptions.RestResponseEntityExceptionHandler;
import com.example.spring5mvcrest.services.ResourceNotFoundException;
import com.example.spring5mvcrest.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.yaml.snakeyaml.events.Event;

import java.util.Arrays;
import java.util.List;

import static com.example.spring5mvcrest.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class VendorControllerTest {  //Using HATEOAS

    public static final long ID = 1L;
    public static final String NAME = "Vendor 1";
    public static final long ID1 = 2L;
    public static final String NAME1 = "Vendor 2";
    public static final String NAME_UPDATE = "Vendor Update";
    @Mock
    VendorService vendorService;

    @Spy
    VendorResourceAssembler vendorResourceAssembler;


    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    VendorDTO vendorDTO1;
    VendorDTO returnDTO;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                        .setControllerAdvice(new RestResponseEntityExceptionHandler())
                        .build();

        vendorDTO1 = VendorDTO.builder().id(ID).name(NAME).build();
        returnDTO = VendorDTO.builder()
                .id(ID)
                .name(vendorDTO1.getName())
                .vendorUrl(VendorController.BASE_URL + "/1")
                .build();


    }

    @Test
    public void getallVendors() throws Exception{
        //given
        VendorDTO vendorDTO = VendorDTO.builder().id(ID1).name(NAME1).build();

        VendorListDTO vendorDTOList = new VendorListDTO(Arrays.asList(vendorDTO, vendorDTO1));

        //when
        when(vendorService.getAllVendors()).thenReturn(vendorDTOList);


/*       String response =         mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        System.out.println(response);*/

        //then
        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));



    }

    @Test
    public void getVendorByName() throws Exception {
        //given

        //when
      //  when(vendorService.getVendorByName(anyString())).thenReturn(vendorDTO1);
        given(vendorService.getVendorByName(anyString())).willReturn(vendorDTO1);

        //then
        mockMvc.perform(get(VendorController.BASE_URL +"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));


    }


    @Test
    public void createNewVendor() throws Exception {
        //given

        //returnDTO


        //when
        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(returnDTO);

        //then

/*        String response =         mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO1)))
                .andReturn().getResponse().getContentAsString();

        System.out.println(response);*/ //for debugging


        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));


    }

    @Test
    public void updateVendor() throws Exception {
        //given

        //returnDTO

        //when
        when(vendorService.saveVendorByDTO(anyLong(),any(VendorDTO.class))).thenReturn(returnDTO);

        //then


/*        String response =         mockMvc.perform(put(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO1)))
                .andReturn().getResponse().getContentAsString();

        System.out.println(response);*/



        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO1)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));


    }

    @Test
    public void patchCustomer() throws Exception{

        //updatedDTO
        VendorDTO updatedDTO = VendorDTO.builder().id(ID)
                .name(NAME_UPDATE)
                .vendorUrl(VendorController.BASE_URL + "/1")
                .build();

        //when
        when(vendorService.patchVendor(anyLong(),any(VendorDTO.class))).thenReturn(updatedDTO);

        //then
        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO1)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name", equalTo(NAME_UPDATE)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));

    }

    @Test
    public void deleteCustomer() throws Exception{

        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        verify(vendorService).deleteVendor(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(vendorService.getVendorByName(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/Test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}