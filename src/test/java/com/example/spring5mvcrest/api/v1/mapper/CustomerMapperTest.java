package com.example.spring5mvcrest.api.v1.mapper;

import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    public static final String FIRST = "TestFirst";
    public static final String LAST = "TestLast";
    public static final long ID = 1L;
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() throws Exception {

        Customer customer = Customer.builder().id(ID).firstname(FIRST).lastname(LAST).build();

        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        //then
        assertEquals(Long.valueOf(ID), customerDTO.getId());
        assertEquals(FIRST, customerDTO.getFirstname());
        assertEquals(LAST, customerDTO.getLastname());
    }
}