package com.example.spring5mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class CustomerDTO {

    @Builder
    public CustomerDTO(Long id, String firstname, String lastname, String customerUrl) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.customerUrl = customerUrl;
    }

    @ApiModelProperty(value = "ID", required = false)
    private Long id;
    @ApiModelProperty(value = "First Name", required = true)
    @Size(min = 2, message = "First Name should have min 2 characters")
    private String firstname;
    @ApiModelProperty(value = "Last Name", required = true)
    @Size(min = 2, message = "Last Name should have min 2 characters")
    private String lastname;

    @ApiModelProperty(value = "Customer URL", required = false)
    @JsonProperty("customer_url")
    private String customerUrl;

}
