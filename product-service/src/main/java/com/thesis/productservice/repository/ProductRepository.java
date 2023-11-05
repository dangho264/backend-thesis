package com.thesis.productservice.repository;

import com.thesis.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Book;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    public Product findById(int id);

    //    @Query("SELECT p FROM Product p ORDER BY p.productId DESC")
//    List<Product> findTop5NewestProducts();
    List<Product> findTop5ByOrderByProductId();
    Page<Product> findAll(Pageable pageable);
//    List<Product> findAllByOrdOrderByPriceDesc();
//    List<Product> findAllByOrdOrderByPriceAsc();
}
