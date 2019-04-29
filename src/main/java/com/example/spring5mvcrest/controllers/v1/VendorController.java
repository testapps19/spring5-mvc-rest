package com.example.spring5mvcrest.controllers.v1;

import com.example.spring5mvcrest.api.v1.model.VendorDTO;
import com.example.spring5mvcrest.api.v1.model.VendorListDTO;
import com.example.spring5mvcrest.config.VendorResourceAssembler;
import com.example.spring5mvcrest.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Api(description = "Vendor API - CRUD operations")
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController { //Using HATEOAS



    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;
    private final VendorResourceAssembler vendorResourceAssembler;

    public VendorController(VendorService vendorService, VendorResourceAssembler vendorResourceAssembler) {
        this.vendorService = vendorService;
        this.vendorResourceAssembler = vendorResourceAssembler;
    }

    @ApiOperation(value = "List of Vendors", notes = "Get all vendors no pagination")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<VendorDTO>> getAllVendors(){
        VendorListDTO vendorListDTO =  vendorService.getAllVendors();
        List<VendorDTO> vendors = vendorListDTO.getVendors();

        return new Resources<>(vendorListDTO.getVendors().stream().map(vendorResourceAssembler::toResource)
                        .collect(Collectors.toList()), linkTo(methodOn(VendorController.class).getAllVendors()).withSelfRel());

    }

    @ApiOperation(value = "Customer by Name")
    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<VendorDTO> getVendorByName(@PathVariable String name){

        return vendorResourceAssembler.toResource(vendorService.getVendorByName(name));

    }

    @ApiOperation(value = "Create Vendor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createNewVendor(@RequestBody VendorDTO vendorDTO) throws URISyntaxException {

        Resource<VendorDTO> resource = vendorResourceAssembler.toResource(vendorService.createNewVendor(vendorDTO));

        return ResponseEntity.created(new URI(resource.getId().expand().getHref().toString())).body(resource);
    }

    @ApiOperation(value = "Update Vendor by ID")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) throws URISyntaxException{

        Resource<VendorDTO> resource = vendorResourceAssembler.toResource(vendorService.saveVendorByDTO(id, vendorDTO));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref().toString()))
                                            .body(resource);
    }

    @ApiOperation(value = "Update Specific set of fields in Vendor by ID")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) throws URISyntaxException{

        Resource<VendorDTO> resource = vendorResourceAssembler.toResource(vendorService.patchVendor(id, vendorDTO));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref().toString()))
                .body(resource);

    }

    @ApiOperation(value = "Delete Vendor")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object>  deleteVendor(@PathVariable Long id){
        vendorService.deleteVendor(id);
        return ResponseEntity.noContent().build();
    }



/*
    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "List of Vendors", notes = "Get all vendors no pagination")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors(){
        return  vendorService.getAllVendors();
    }

    @ApiOperation(value = "Customer by Name")
    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorByName(@PathVariable String name){
        return vendorService.getVendorByName(name);
    }

    @ApiOperation(value = "Create Vendor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO){

        return vendorService.createNewVendor(vendorDTO);
    }

    @ApiOperation(value = "Update Vendor by ID")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return  vendorService.saveVendorByDTO(id, vendorDTO);
    }

    @ApiOperation(value = "Update Specific set of fields in Vendor by ID")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return  vendorService.patchVendor(id, vendorDTO);
    }

    @ApiOperation(value = "Delete Vendor")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void  deleteVendor(@PathVariable Long id){
        vendorService.deleteVendor(id);
    }*/

}
