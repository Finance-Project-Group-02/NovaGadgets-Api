package com.Group_02.NovaGadgets_Api.category.controller;

import com.Group_02.NovaGadgets_Api.category.model.CategoryEntity;
import com.Group_02.NovaGadgets_Api.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryEntity>> getAllCategories(){
        return new ResponseEntity<List<CategoryEntity>>(categoryService.findAll(), HttpStatus.OK);
    }
}
