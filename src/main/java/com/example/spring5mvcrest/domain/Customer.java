package com.example.spring5mvcrest.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends BaseEntity{

 @Builder
 public Customer(Long id, String firstname, String lastname) {
  super(id);
  this.firstname = firstname;
  this.lastname = lastname;
 }

 private String firstname;
 private String lastname;

}
