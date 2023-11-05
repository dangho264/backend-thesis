package com.thesis.productservice.controller;

import com.thesis.productservice.entity.Category;
import com.thesis.productservice.entity.Product;
import com.thesis.productservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping
    public List<Category> getCategory(){
        return categoryRepository.findAll();
    }
}
