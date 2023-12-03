package com.thesis.productservice.controller;

import com.thesis.productservice.entity.Artist;
import com.thesis.productservice.entity.Category;
import com.thesis.productservice.entity.Product;
import com.thesis.productservice.entity.TypeProduct;
import com.thesis.productservice.repository.ArtistRepository;
import com.thesis.productservice.repository.CategoryRepository;
import com.thesis.productservice.repository.TypeProductRepository;
import com.thesis.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/filter")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private TypeProductRepository typeProductRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @GetMapping("/category")
    public List<Category> getCategory(){
        return categoryRepository.findAll();
    }
    @GetMapping("/type")
    public List<TypeProduct> getTypeProduct(){
        return typeProductRepository.findAll();
    }
    @GetMapping("/artist")
    public List<Artist> getArtist(){
        return artistRepository.findAll();
    }
    @GetMapping("/category/search")
    public Page<Product> filterProductByCategory(@RequestParam(name = "page") int page,
                                       @RequestParam(name = "size") int size,
                                       @RequestParam(name="id") int id){
        Pageable pageable = PageRequest.of(page, size);

        return productService.getProductByCategory(id, pageable);
    }
    @GetMapping("/type/search")
    public Page<Product> filterProductByType(@RequestParam(name = "page") int page,
                                                 @RequestParam(name = "size") int size,
                                                 @RequestParam(name="id") int id){
        Pageable pageable = PageRequest.of(page, size);
        return productService.getProductByType(id, pageable);
    }
}
