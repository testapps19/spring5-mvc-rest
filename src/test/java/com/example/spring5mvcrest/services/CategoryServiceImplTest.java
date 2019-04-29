package com.example.spring5mvcrest.services;

import com.example.spring5mvcrest.api.v1.mapper.CategoryMapper;
import com.example.spring5mvcrest.api.v1.model.CategoryDTO;
import com.example.spring5mvcrest.domain.Category;
import com.example.spring5mvcrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    public static final Long ID = 1L;
    public static final String Name = "Test";
    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);

    }

    @Test
    void getAllCategories() throws Exception{

        //given
        List<Category> categoryList = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categoryList);

        //when
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        //then
        assertEquals(3, categoryDTOS.size());

    }

    @Test
    void getCategoryByName() throws Exception {


        //given
        Category category = Category.builder().id(ID).name(Name).build();

        when(categoryRepository.findByName(anyString())).thenReturn(category);

        //when
        CategoryDTO categoryDTO = categoryService.getCategoryByName(Name);

        //then
        assertEquals(ID, categoryDTO.getId());
        assertEquals(Name, categoryDTO.getName());


    }
}