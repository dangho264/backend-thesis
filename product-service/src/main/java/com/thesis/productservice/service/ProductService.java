package com.thesis.productservice.service;

import com.thesis.productservice.dto.AppException;
import com.thesis.productservice.dto.ProductDTO;
import com.thesis.productservice.dto.ProductPutDTO;
import com.thesis.productservice.entity.Artist;
import com.thesis.productservice.entity.Category;
import com.thesis.productservice.entity.Price;
import com.thesis.productservice.entity.Product;
import com.thesis.productservice.repository.ArtistRepository;
import com.thesis.productservice.repository.CategoryRepository;
import com.thesis.productservice.repository.PriceRepository;
import com.thesis.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ArtistRepository artistRepository;
    public List<Product> findTop5NewestProducts(){
        return productRepository.findTop5ByOrderByProductId();
    }
    public long addProduct(ProductDTO productDTO) {
//        DecimalFormat format = new DecimalFormat("#,###.##");
//        String priceFormat = format.format(productDTO.getPrice());
        // Tạo một đối tượng Product từ dữ liệu trong ProductRequest
        Product product = new Product();
        product.setName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setImage_url(productDTO.getImage_url());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setSellerName(product.getSellerName());
        product.setStatus(false);
        Optional<Category> category = categoryRepository.findById(productDTO.getCategory_id());
        // Kiểm tra xem các thông tin Category, Seller, và Artist có hợp lệ không
        if (category.isEmpty()) {
            throw new RuntimeException("Category không hợp lệ.");
        }
        product.setCategory(category.get());
        if(productDTO.getQuantity() > 0){
            product.setStatus(true);
        }

        // Lưu sản phẩm vào cơ sở dữ liệu
        productRepository.save(product);

        Price price = new Price();
        price.setProduct(product);
        price.setPrice(product.getPrice());
        price.setTimestamp(LocalDateTime.now());
        priceRepository.save(price);
        Set<Artist> artists = new HashSet<>();
        for(String artistName : productDTO.getArtistName()){
            Artist artist = new Artist();
            if(artistRepository.existsByArtistName(artistName)) {
                artist.setProduct(product);
                artist.setArtistName(artistName);
                artistRepository.save(artist);
                artists.add(artist);
            }
        }
        product.setArtists(artists);
        productRepository.save(product);
        return product.getProductId();
    }
    public long updateProduct(ProductPutDTO productDTO) {
        // Tạo một đối tượng Product từ dữ liệu trong ProductRequest
        Product product = productRepository.findById(productDTO.getId());
        if(product == null){
            throw new AppException("Product not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            product.setName(productDTO.getProductName());
            product.setDescription(productDTO.getDescription());
            product.setImage_url(productDTO.getImage_url());
            product.setQuantity(productDTO.getQuantity());
            product.setPrice(productDTO.getPrice());
            product.setSellerName(productDTO.getSellerName());
            Optional<Category> category = categoryRepository.findById(productDTO.getCategory_id());
            // Kiểm tra xem các thông tin Category, Seller, và Artist có hợp lệ không
            if (category.isEmpty()) {
                throw new AppException("Category không hợp lệ.", HttpStatus.NOT_FOUND);
            }
            product.setCategory(category.get());
            if (productDTO.getQuantity() > 0) {
                product.setStatus(true);
            }

            // Lưu sản phẩm vào cơ sở dữ liệu
            productRepository.save(product);

            Price price = new Price();
            price.setProduct(product);
            price.setPrice(product.getPrice());
            price.setTimestamp(LocalDateTime.now());
            priceRepository.save(price);
            Set<Artist> artists = new HashSet<>();
            for (String artistName : productDTO.getArtistName()) {
                Artist artist = new Artist();
                if (artistRepository.existsByArtistName(artistName)) {
                    artist.setProduct(product);
                    artist.setArtistName(artistName);
                    artistRepository.save(artist);
                    artists.add(artist);
                }
            }
            product.setArtists(artists);
            productRepository.save(product);
        }
        return product.getProductId();
    }
    public long verifyProduct(ProductPutDTO productDTO) {
        // Tạo một đối tượng Product từ dữ liệu trong ProductRequest
        Product product = productRepository.findById(productDTO.getId());
        if(product == null){
            throw new AppException("Product not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            if (!product.isStatus()){
                product.setStatus(true);
            }
            else{
                throw new AppException("Product is verified", HttpStatus.NOT_MODIFIED);
            }
        }
        return product.getProductId();
    }
    public void deleteProductById(int id){
        Product product = productRepository.findById(id);
        if(product == null){
            throw new AppException("Product not exist", HttpStatus.BAD_REQUEST);
        }
    }
    public List<Product> getListProduct(){
        List<Product> productFindAll = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        for(Product product : productFindAll){
            if(product.isStatus()){
                products.add(product);
            }
        }
        return products;
    }
    public Product findById(int id){
        return productRepository.findById(id);
    }

    public Boolean isExistProduct(int id){
        return productRepository.existsById(id);
    }
}

