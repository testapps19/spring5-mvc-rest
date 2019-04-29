package com.example.spring5mvcrest.controllers.v1;

import com.example.spring5mvcrest.api.v1.model.CategoryDTO;
import com.example.spring5mvcrest.api.v1.model.CategoryListDTO;
import com.example.spring5mvcrest.services.CategoryService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
 //   public ResponseEntity<CategoryListDTO> getallCategories(){
    public MappingJacksonValue getallCategories(){

        //dynamic Filtering

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name");
        FilterProvider filters = new SimpleFilterProvider().addFilter("DescFilter", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(new ResponseEntity<CategoryListDTO>(
                new CategoryListDTO(categoryService.getAllCategories()), HttpStatus.OK));

        mapping.setFilters(filters);

        return mapping;

/*        return new ResponseEntity<CategoryListDTO>(
                    new CategoryListDTO(categoryService.getAllCategories()), HttpStatus.OK);*/
    }

    @GetMapping("{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name){

        return new ResponseEntity<CategoryDTO>(categoryService.getCategoryByName(name), HttpStatus.OK);
    }

}
