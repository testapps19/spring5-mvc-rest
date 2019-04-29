package com.example.spring5mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class VendorDTO {

    @Builder
    public VendorDTO(Long id, String name, String vendorUrl) {
        this.id = id;
        this.name = name;
        this.vendorUrl = vendorUrl;
    }

    @ApiModelProperty(value = "ID", required = false)
    private Long id;

    @ApiModelProperty(value = "Vendor Name", required = true, notes = "Should have min 2 characters")
    @Size(min = 2, message = "Name should have min 2 characters")
    private String name;

    @ApiModelProperty(value = "Vendor URL", required = false)
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
