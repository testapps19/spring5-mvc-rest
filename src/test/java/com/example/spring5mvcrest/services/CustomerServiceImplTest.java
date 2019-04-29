package com.example.spring5mvcrest.services;

import com.example.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.domain.Customer;
import com.example.spring5mvcrest.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    public static final long ID = 1L;
    public static final String FIRSTNAME = "First";
    public static final String LASTNAME = "Last";
    public static final String URI = "/api/v1/customers/1";
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    CustomerDTO customerDTO;
    Customer customer;


    @BeforeEach
    void setUp() {

      //  MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);

        customerDTO = CustomerDTO.builder().id(ID).firstname(FIRSTNAME).lastname(LASTNAME).build();

        customer = Customer.builder().id(ID).firstname(customerDTO.getFirstname()).lastname(customerDTO.getLastname()).build();



    }

    @Test
    public void getAllCustomers() throws Exception{

        //given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(3, customerDTOS.size());

    }

    @Test
    public void getCustomerById() throws Exception {

        //given
      //  Customer customer = Customer.builder().id(ID).firstname(FIRSTNAME).lastname(LASTNAME).build();

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        //then
        assertEquals(Long.valueOf(ID), customerDTO.getId());
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());

    }

    @Test
    public void createNewCustomer() throws  Exception{

        //given

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        //when
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(), savedDTO.getFirstname());
        assertEquals(customerDTO.getLastname(), savedDTO.getLastname());
        assertEquals(URI, savedDTO.getCustomerUrl());


    }

    @Test
    public void saveCustomerByDTO() throws Exception{

        //given

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        //when
        CustomerDTO savedDTO = customerService.saveCustomerByDTO(ID, customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(), savedDTO.getFirstname());
        assertEquals(URI, savedDTO.getCustomerUrl());
    }

    @Test
    public void patchCustomer() throws Exception {

        //given

        Customer customer1 = Customer.builder().id(ID).firstname(FIRSTNAME).lastname(LASTNAME).build();

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        //when
        CustomerDTO savedDTO = customerService.patchCustomer(ID, customerDTO);


        //then
        assertEquals(customerDTO.getFirstname(), savedDTO.getFirstname());
        assertEquals(customerDTO.getLastname(), savedDTO.getLastname());
    }

    @Test
    public void deleteCustomer() throws Exception {

        customerRepository.deleteById(ID);

        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}