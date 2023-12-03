package com.thesis.productservice.repository;

import com.thesis.productservice.entity.Category;
import com.thesis.productservice.entity.Product;
import com.thesis.productservice.entity.TypeProduct;
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
//    List<Product> findProductBySellerName(String name);
    Page<Product> findProductBySellerName(String name, Pageable pageable);
    List<Product> findProductBySellerName(String name);
    Page<Product> findProductByStatus(Boolean value, Pageable pageable);
    Page<Product> getProductByOrderByPriceAsc(Pageable pageable);
    Page<Product> getProductByOrderByPriceDesc(Pageable pageable);
    public int countProductBySellerName(String sellerName);
    Page<Product> findByNameIgnoreCaseContaining(String keyword, Pageable pageable);
    Page<Product> findProductByCategory(Category category, Pageable pageable);
    Page<Product> findProductByType(TypeProduct typeProduct, Pageable pageable);

}
