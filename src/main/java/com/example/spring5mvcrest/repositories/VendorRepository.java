package com.example.spring5mvcrest.repositories;

import com.example.spring5mvcrest.api.v1.model.VendorDTO;
import com.example.spring5mvcrest.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Vendor findByName(String name);
}
