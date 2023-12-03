package com.thesis.productservice.repository;

import com.thesis.productservice.entity.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeProductRepository extends JpaRepository<TypeProduct, Integer> {
}
