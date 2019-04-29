package com.example.spring5mvcrest.controllers.v1;

import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.api.v1.model.CustomerListDTO;
import com.example.spring5mvcrest.domain.Customer;
import com.example.spring5mvcrest.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import javax.validation.Valid;

@Api(description = "Customer API - CRUD operations")
@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers"; // not maitaining it in properties file because we dont want to wireup the spring environment
                                                                // for getting this value and this value is static not changing at runtime

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "List of Customers", notes = "Get all customers no pagination")
    @GetMapping
    public ResponseEntity<CustomerListDTO> getallCustomers(){
           return new ResponseEntity<CustomerListDTO>(
                   new CustomerListDTO(customerService.getAllCustomers()), HttpStatus.OK);
    }

    @ApiOperation(value = "Customer by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){
          return new ResponseEntity<CustomerDTO>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Create Customer")
    @PostMapping
    public ResponseEntity<CustomerDTO> createNewCustomer(@Valid @RequestBody CustomerDTO customerDTO){
          return  new ResponseEntity<CustomerDTO>(customerService.createNewCustomer(customerDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Customer by ID")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        return  new ResponseEntity<CustomerDTO>(customerService.saveCustomerByDTO(id, customerDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Update Specific set of fields in Customer by ID")
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        return  new ResponseEntity<CustomerDTO>(customerService.patchCustomer(id, customerDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Customer")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return  new ResponseEntity<Void>(HttpStatus.OK);
    }
}
