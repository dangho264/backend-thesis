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
    public Page<Product> getProduct_ToCheck(@RequestParam(name = "page") int page,
                                            @RequestParam(name = "size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getPageProduct_ToCheck(pageable);
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
    @GetMapping("/sort")
    public Page<Product> getProductPagingSorting(@RequestParam(name = "page") int page,
                                                  @RequestParam(name = "size") int size,
                                                  @RequestParam(name = "sort") String sort) {
        Pageable pageable = PageRequest.of(page, size);
        if(sort.equalsIgnoreCase("acs")){
            return productRepository.getProductByOrderByPriceAsc(pageable);
        }
        return productRepository.getProductByOrderByPriceDesc(pageable);
    }
    @PostMapping("/verify-product/{productId}")
    public void verifyProduct(@PathVariable("productId") int productId){
        productService.verifyProduct(productId);
    }
    @GetMapping("/count-product/{seller}")
    public int verifyProduct(@PathVariable("seller") String seller){
        return productRepository.countProductBySellerName(seller);
    }
    @GetMapping("/search")
    public Page<Product> searchProductByKeyword(@RequestParam(name = "page") int page,
                                                @RequestParam(name = "size") int size,
                                                @RequestParam(name="keyword") String keyword){
        Pageable pageable = PageRequest.of(page, size);
        return productService.getPageProductByKeyword(keyword,pageable);
    }
    @GetMapping("/product-to-revenue/{seller}")
    public List<Product> getProductBySeller(@PathVariable("seller") String seller){
        return productRepository.findProductBySellerName(seller);
    }
}
