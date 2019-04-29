package com.example.spring5mvcrest.api.v1.mapper;

import com.example.spring5mvcrest.api.v1.model.CategoryDTO;
import com.example.spring5mvcrest.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    public static final String NAME = "Test";
    public static final long ID = 1L;
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
   public void CategoryToCategoryDTO() throws Exception{

        //given
       Category category = Category.builder().id(ID).name(NAME).build();


       //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

       //then
       assertEquals(Long.valueOf(ID), categoryDTO.getId());
       assertEquals(NAME, categoryDTO.getName());

    }
}