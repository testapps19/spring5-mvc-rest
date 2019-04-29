package com.example.spring5mvcrest.domain;

import lombok.*;

import javax.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category extends BaseEntity{

    @Builder
    public Category(Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    private String name;

    private String description;


}
