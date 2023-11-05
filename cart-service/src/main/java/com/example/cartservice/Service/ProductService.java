package com.example.cartservice.Service;

import com.example.cartservice.DTO.ProductVm;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class ProductService {
    private final WebClient webClient;

    public ProductService(WebClient webClient) {
        this.webClient = webClient;
    }
    public ProductVm getProducts(int ids) {
        final URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:9004")
                .path("/product")
                .build()
                .toUri();
        return webClient.get()
                .uri(String.format("/%d",ids))
                .retrieve()
                .bodyToFlux(ProductVm.class)
                .blockFirst();
    }
}
