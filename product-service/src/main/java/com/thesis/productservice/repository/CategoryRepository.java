package com.thesis.productservice.repository;

import com.thesis.productservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
//    public Category findBy(int id);
}
