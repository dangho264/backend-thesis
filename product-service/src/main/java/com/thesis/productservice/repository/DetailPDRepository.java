package com.thesis.productservice.repository;

import com.thesis.productservice.entity.DetailProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailPDRepository extends JpaRepository<DetailProduct, Integer> {
}
