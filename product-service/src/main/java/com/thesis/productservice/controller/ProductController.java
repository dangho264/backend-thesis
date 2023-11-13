package com.thesis.productservice.controller;

import com.thesis.productservice.dto.ProductDTO;
import com.thesis.productservice.dto.ProductPutDTO;
import com.thesis.productservice.entity.Product;
import com.thesis.productservice.repository.ProductRepository;
import com.thesis.productservice.service.ProductService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add-product")
    public ResponseEntity<Long> addProduct(@RequestBody ProductDTO productDTO) {
        long productId = productService.addProduct(productDTO);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @PutMapping("/update-product")
    public ResponseEntity<Long> updateProduct(@RequestBody ProductPutDTO productDTO) {
        long productId = productService.updateProduct(productDTO);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Product> getProduct() {
        return productService.getListProduct();
    }

    @GetMapping("/ToCheck")
    public List<Product> getProduct_ToCheck() {
        return productService.getListProduct_ToCheck();
    }

    @GetMapping("/newArrival")
    public List<Product> getNewArrivalProduct() {
        return productService.findTop5NewestProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable() int id) {
        return productService.findById(id);
    }

    @GetMapping("/isExist/{id}")
    public Boolean checkExistProduct(@PathVariable int id) {
        return productService.isExistProduct(id);
    }

    @GetMapping("page")
    public Page<Product> getProductByPage(@RequestParam(name = "page") int page,
                                          @RequestParam(name = "size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }
    @GetMapping("/page-seller")

    public Page<Product> getProductPagingOfSeller(@RequestParam(name = "page") int page,
                                                  @RequestParam(name = "size") int size,
                                                  @RequestParam(name = "sellerName") String sellerName) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findProductBySellerName(sellerName,pageable);
    }
}
