package com.example.spring5mvcrest.config;

import com.example.spring5mvcrest.api.v1.model.VendorDTO;
import com.example.spring5mvcrest.controllers.v1.VendorController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class VendorResourceAssembler implements ResourceAssembler<VendorDTO, Resource<VendorDTO>> {

    @Override
    public Resource<VendorDTO> toResource(VendorDTO vendorDTO) {


        return new Resource<>(vendorDTO, linkTo(methodOn(VendorController.class).getVendorByName(vendorDTO.getName())).withSelfRel(),
                                         linkTo(methodOn(VendorController.class).getAllVendors()).withRel("vendors"));

    }

}

//