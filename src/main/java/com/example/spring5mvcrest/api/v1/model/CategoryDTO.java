package com.example.spring5mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDTO {

    @Builder
    public CategoryDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    private Long id;
    private String name;
    //@JsonIgnore - Static filtering
    private String description;

}
