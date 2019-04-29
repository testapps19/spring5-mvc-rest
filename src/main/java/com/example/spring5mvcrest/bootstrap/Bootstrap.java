package com.example.spring5mvcrest.bootstrap;

import com.example.spring5mvcrest.domain.Category;
import com.example.spring5mvcrest.domain.Customer;
import com.example.spring5mvcrest.domain.Vendor;
import com.example.spring5mvcrest.repositories.CategoryRepository;
import com.example.spring5mvcrest.repositories.CustomerRepository;
import com.example.spring5mvcrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

private final CategoryRepository categoryRepository;
private final CustomerRepository customerRepository;
private final VendorRepository   vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        loadCategory();

        loadCustomers();

        loadVendors();

    }

    private void loadVendors() {
        vendorRepository.save(Vendor.builder().name("Vendor 1").build());
        vendorRepository.save(Vendor.builder().name("Vendor 2").build());

        System.out.println("Vendors Loaded : " + vendorRepository.count());
    }

    private void loadCustomers() {
        customerRepository.save(Customer.builder().firstname("TestFirst1").lastname("TestLast1").build());
        customerRepository.save(Customer.builder().firstname("TestFirst2").lastname("TestLast2").build());

        System.out.println("Customers Loaded : " + customerRepository.count());
    }

    private void loadCategory() {
        categoryRepository.save(Category.builder().name("Fruits").description("Category 1").build());
        categoryRepository.save(Category.builder().name("Dried").description("Category 1").build());
        categoryRepository.save(Category.builder().name("Fresh").description("Category 2").build());
        categoryRepository.save(Category.builder().name("Exotic").description("Category 2").build());
        categoryRepository.save(Category.builder().name("Nuts").description("Category 3").build());


        System.out.println("Category Loaded : " + categoryRepository.count());
    }
}
