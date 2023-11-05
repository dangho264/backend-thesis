package com.example.cartservice.Controller;

import com.example.cartservice.DTO.CartDTO;
import com.example.cartservice.Entity.Cart;
import com.example.cartservice.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping("/add")
    public void addToBasket(@RequestBody CartDTO req){
        cartService.add(req);
    }
    @GetMapping("/{username}")
    public ResponseEntity<Cart> getBasket(@PathVariable String username) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(cartService.getCart(username));
    }
    @DeleteMapping("/delete/{username}")
    public String deleteBasket(@PathVariable String username) {
        return cartService.delete(username);
    }
}
