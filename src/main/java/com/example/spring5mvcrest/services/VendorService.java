package com.example.spring5mvcrest.services;

import com.example.spring5mvcrest.api.v1.model.VendorDTO;
import com.example.spring5mvcrest.api.v1.model.VendorListDTO;

import java.util.List;

public interface VendorService {

    VendorListDTO getAllVendors();

    VendorDTO getVendorByName(String  name);

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

    void deleteVendor(Long id);

}
