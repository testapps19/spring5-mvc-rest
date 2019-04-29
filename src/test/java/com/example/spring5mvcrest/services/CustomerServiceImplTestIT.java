package com.example.spring5mvcrest.services;

import com.example.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.bootstrap.Bootstrap;
import com.example.spring5mvcrest.domain.Customer;
import com.example.spring5mvcrest.repositories.CategoryRepository;
import com.example.spring5mvcrest.repositories.CustomerRepository;
import com.example.spring5mvcrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsEqual.equalTo;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CustomerServiceImplTestIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository   vendorRepository;

    CustomerService customerService;

    Long customerId;

    @BeforeEach
    public void setUp() throws  Exception{
        System.out.println("Customer Data :" + customerRepository.findAll().size());

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();//load data

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);

        List<Customer> customers = customerRepository.findAll();
        customerId = customers.get(0).getId();
    }

    @Test
    public void patchCustomerFirstName() throws Exception {


        String updateName = "UpdateFirst";
        Customer customer = customerRepository.getOne(customerId);
        assertNotNull(customer);

        String firstName = customer.getFirstname();

        CustomerDTO customerDTO = CustomerDTO.builder().firstname(updateName).build();

        customerService.patchCustomer(customerId, customerDTO);

        Customer updatedCustomer = customerRepository.findById(customerId).get();

        assertNotNull(updatedCustomer);
        assertEquals(updateName, updatedCustomer.getFirstname());
        assertThat(firstName, not(equalTo(updatedCustomer.getFirstname())));


    }

    @Test
    public void patchCustomerLastName() throws Exception {


        String updateName = "UpdateLast";
        Customer customer = customerRepository.getOne(customerId);
        assertNotNull(customer);

        String lastName = customer.getLastname();

        CustomerDTO customerDTO1 = CustomerDTO.builder().lastname(updateName).build();

        customerService.patchCustomer(customerId, customerDTO1);

        Customer updatedCustomer = customerRepository.findById(customerId).get();

        assertNotNull(updatedCustomer);
        assertEquals(updateName, updatedCustomer.getLastname());
        assertThat(lastName, not(equalTo(updatedCustomer.getLastname())));


    }
}