package com.example.spring5mvcrest.services;

import com.example.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.controllers.v1.CustomerController;
import com.example.spring5mvcrest.domain.Customer;
import com.example.spring5mvcrest.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map( customer -> {

                       CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                       customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));

                       return customerDTO;

                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {


        return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customerDTO));

    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {

        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);

    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {

        return customerRepository.findById(id)
                                 .map( customer -> {

                                     if(customerDTO.getFirstname() != null){
                                         customer.setFirstname(customerDTO.getFirstname());
                                     }

                                     if(customerDTO.getLastname() != null){
                                         customer.setLastname(customerDTO.getLastname());
                                     }

                                     CustomerDTO customerDTO1 = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
                                     customerDTO1.setCustomerUrl(getCustomerUrl(id));
                                     return customerDTO1;
                                 }).orElseThrow(ResourceNotFoundException::new);


    }

    @Override
    public void deleteCustomer(Long id) {
         customerRepository.deleteById(id);
    }


    private CustomerDTO saveAndReturnDTO(Customer customer){
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        returnDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));

        return returnDTO;
    }

    private String getCustomerUrl(Long id) {
        return CustomerController.BASE_URL + "/" + id;
    }
}
