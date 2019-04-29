package com.example.spring5mvcrest.domain;

import lombok.*;

import javax.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vendor extends BaseEntity {

    @Builder
    public Vendor(Long id, String name) {
        super(id);
        this.name = name;
    }

    private String name;

}
