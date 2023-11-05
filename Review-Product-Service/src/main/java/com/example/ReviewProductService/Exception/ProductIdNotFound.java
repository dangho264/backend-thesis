package com.example.ReviewProductService.Exception;

public class ProductIdNotFound extends RuntimeException{
    public ProductIdNotFound(String message) {
        super(message);
    }
}
