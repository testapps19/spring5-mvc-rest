package com.example.spring5mvcrest.services;

import com.example.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.example.spring5mvcrest.api.v1.model.VendorDTO;
import com.example.spring5mvcrest.api.v1.model.VendorListDTO;
import com.example.spring5mvcrest.controllers.v1.VendorController;
import com.example.spring5mvcrest.domain.Vendor;
import com.example.spring5mvcrest.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    VendorMapper vendorMapper;
    VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorListDTO getAllVendors() {

        List<VendorDTO> vendorDTOS = vendorRepository
                .findAll()
                .stream()
                .map(vendor -> {
                      VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                      vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));

                      return vendorDTO;
                })
                .collect(Collectors.toList());

        return new VendorListDTO(vendorDTOS);
    }



    @Override
    public VendorDTO getVendorByName(String name) {
        Vendor vendor = vendorRepository.findByName(name);
                if(vendor == null)
                throw  new ResourceNotFoundException("name : " + name);

         return vendorMapper.vendorToVendorDTO(vendor);

    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnDTO(vendorMapper.vendorDtoToVendor(vendorDTO));
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {

        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);

        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {

        return vendorRepository.findById(id)
                                .map( vendor -> {

                                    if(vendorDTO.getName() != null){
                                        vendor.setName(vendorDTO.getName());
                                    }

                                    VendorDTO vendorDTO1 = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
                                    vendorDTO1.setVendorUrl(getVendorUrl(id));

                                    return vendorDTO1;
                                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendor(Long id) {

        vendorRepository.deleteById(id);

    }

    private VendorDTO saveAndReturnDTO(Vendor vendor) {

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
        vendorDTO.setVendorUrl(getVendorUrl(vendorDTO.getId()));
        return vendorDTO;

    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }
}
