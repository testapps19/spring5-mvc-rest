package com.example.spring5mvcrest.controllers.v1;

import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.exceptions.RestResponseEntityExceptionHandler;
import com.example.spring5mvcrest.services.CustomerService;
import com.example.spring5mvcrest.services.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.example.spring5mvcrest.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    public static final long ID = 1L;
    public static final String FIRSTNAME = "First1";
    public static final String LASTNAME = "Last1";
    public static final long ID1 = 2L;
    public static final String FIRSTNAME1 = "First2";
    public static final String LASTNAME1 = "Last2";
    public static final String UPDATE_FIRST = "UpdateFirst";
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    CustomerDTO customerDTO1;
    CustomerDTO returnDTO;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

        customerDTO1 = CustomerDTO.builder().id(ID).firstname(FIRSTNAME).lastname(LASTNAME).build();

        returnDTO = CustomerDTO.builder().id(ID)
                .firstname(customerDTO1.getFirstname())
                .lastname(customerDTO1.getLastname())
                .customerUrl(CustomerController.BASE_URL + "/1")
                .build();


    }

    @Test
   public void getallCustomers() throws Exception{

        //given
        CustomerDTO customerDTO2 = CustomerDTO.builder().id(ID1).firstname(FIRSTNAME1).lastname(LASTNAME1).build();

        List<CustomerDTO> customerDTOS = Arrays.asList(customerDTO1, customerDTO2);

        //when
        when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        //then
        mockMvc.perform(get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));



    }

    @Test
    public void getCustomerById() throws Exception {
        //given

        //when
        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO1);

        //then
        mockMvc.perform(get(CustomerController.BASE_URL +"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)));


    }


    @Test
    public void createNewCustomer() throws Exception {
        //given

        //returnDTO


        //when
        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(returnDTO);

        //then

/*        String response =         mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO1)))
                .andReturn().getResponse().getContentAsString();

        System.out.println(response);*/ //for debugging


        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));


    }

    @Test
    public void updateCustomer() throws Exception {
        //given

        //returnDTO

        //when
        when(customerService.saveCustomerByDTO(anyLong(),any(CustomerDTO.class))).thenReturn(returnDTO);

        //then


        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));


    }

    @Test
    public void patchCustomer() throws Exception{

        //updatedDTO
       CustomerDTO updatedDTO = CustomerDTO.builder().id(ID)
                                            .firstname(UPDATE_FIRST)
                                            .lastname(customerDTO1.getLastname())
                                            .customerUrl(CustomerController.BASE_URL + "/1")
                                            .build();

        //when
        when(customerService.patchCustomer(anyLong(),any(CustomerDTO.class))).thenReturn(updatedDTO);

        //then
        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(UPDATE_FIRST)))
                .andExpect(jsonPath("$.lastname", equalTo(customerDTO1.getLastname())))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));

    }

    @Test
    public void deleteCustomer() throws Exception{

        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomer(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}