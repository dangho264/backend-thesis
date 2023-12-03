package com.example.cartservice.Controller;

import com.example.cartservice.DTO.CartDTO;
import com.example.cartservice.DTO.CartResponseDTO;
import com.example.cartservice.DTO.CartUpdateDTO;
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
    public void addToBasket(@RequestBody CartDTO req) {
        cartService.add(req);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Cart> getBasket(@PathVariable String username) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(cartService.getCart(username));
    }

    @GetMapping("/selected/{username}")
    public ResponseEntity<Cart> getCartSelected(@PathVariable String username) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(cartService.getCartSelected(username));
    }

    @GetMapping("/seller/{username}")
    public ResponseEntity<CartResponseDTO> getCartBySeller(@PathVariable String username) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(cartService.getCartBySeller(username));
    }

    @DeleteMapping("/delete/{username}")
    public String deleteBasket(@PathVariable String username) {
        return cartService.delete(username);
    }

    @DeleteMapping("/delete")
    public String deleteItemInBasket(@RequestParam(name = "username") String username,
                                     @RequestParam(name = "id") int id) {
        return cartService.removeItem(id,username);
    }

    @PutMapping("/select-item")
    public void selectItem(@RequestBody CartUpdateDTO cartUpdateDTO) {
        cartService.selectedItemInCart(cartUpdateDTO);
    }

    @PutMapping("/notselect-item/{username}")
    public void selectItem(@PathVariable String username) {
        cartService.setCartNotSelected(username);
    }
}
