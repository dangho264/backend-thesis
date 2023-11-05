package com.example.cartservice.Service;

import com.example.cartservice.DTO.AppException;
import com.example.cartservice.DTO.CartDTO;
import com.example.cartservice.Entity.Cart;
import com.example.cartservice.Entity.CartItem;
import com.example.cartservice.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Optional<Cart> getCartByUserId(String userId) throws ExecutionException, InterruptedException {
        Optional<Cart> carts = cartRepository.findById(userId);
        return carts;
    }
    public Optional<Cart> getCartById(String username) throws ExecutionException, InterruptedException {
        Optional<Cart> carts = cartRepository.findById(username);
        return carts;
    }
    public Cart getCart(String username) throws ExecutionException, InterruptedException {
        Optional<Cart> existingCart = cartRepository.findById(username);
        return existingCart.isPresent() && existingCart.get() != null ? existingCart.get() : null;
    }
    public CartItem findCartItemById(Set<CartItem> cartItems, int targetId) {
        for (CartItem item :
                cartItems) {
            if (item.getProductId() == targetId) {
                return item;
            }
        } // Trả về phần tử có id khớp hoặc null nếu không tìm thấy
        return null;
    }

    public void add(CartDTO req) {
        Optional<Cart> existingCart = cartRepository.findById(req.getUsername());
        if (existingCart.isEmpty()) {
//            Cart cart = Cart.builder().id(req.getId()).username(req.getUsername()).totalPrice(req.getPrice()).cartItems(new HashSet<>()).build();
            Cart cart = Cart.builder().id(req.getUsername()).totalPrice(req.getPrice()).cartItems(new HashSet<>()).build();

            cartRepository.save(cart);
//            existingCart = cartRepository.findById(req.getId());
            existingCart = cartRepository.findById(req.getUsername());
        }

        Set<CartItem> cartItems = new HashSet<>();
        if(existingCart.get().getCartItems() == null || existingCart.get().getCartItems().isEmpty()) {
            cartItems.add(new CartItem(cartItems.size(), req.getProductId(), req.getPrice(), req.getImage_url(),req.getName(), 1, req.getSellerName()));
        } else {
            cartItems = existingCart.get().getCartItems();
            CartItem cartItem = findCartItemById(existingCart.get().getCartItems(), req.getProductId());
            if (cartItem != null) {
                cartItems.remove(cartItem);
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItems.add(cartItem);
                List<CartItem> cartList = new ArrayList<>(cartItems);

                // Sử dụng Comparator để sắp xếp danh sách theo ID tăng dần
                Collections.sort(cartList, Comparator.comparing(CartItem::getId));

                // Chuyển danh sách trở lại thành một Set
                cartItems = new LinkedHashSet<>(cartList);

            } else {
                cartItems.add(new CartItem(cartItems.size(), req.getProductId(), req.getPrice(), req.getImage_url(),req.getName(), 1, req.getSellerName()));
            }

        }
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item :
                cartItems) {
            total = total.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        existingCart.get().setTotalPrice(total);
        existingCart.get().setCartItems(cartItems);
        cartRepository.save(existingCart.get());
    }
    public String delete(String username) {
        cartRepository.deleteById(username);
        return String.format("%s is remove", username);
    }
    public String removeItem(int id, String name) {
        Optional<Cart> existingCart = cartRepository.findById(name);
        if (existingCart.isEmpty()) {
            throw new AppException("Khong co gio hang", HttpStatus.NOT_FOUND);
        }
        Set<CartItem> CartItems = existingCart.get().getCartItems();
        CartItem CartItem = findCartItemById(CartItems,id);
        if(CartItem != null){
            CartItems.remove(CartItem);
        }else {
            throw new AppException("Sản phẩm không có trong giỏ hàng", HttpStatus.NOT_FOUND);
        }
        existingCart.get().setCartItems(CartItems);
        existingCart.get().setTotalPrice(existingCart.get().getTotalPrice().subtract(CartItem.getPrice().multiply(new BigDecimal(CartItem.getQuantity()))));
        cartRepository.save(existingCart.get());
        return String.format("%s đã được xóa", name);
    }
    @KafkaListener(topics = "cart-to-order", containerFactory = "kafkaListenerContainerFactory")
    public String printProduct(String username){
        delete(username);
        System.out.println(username);
        return username;
    }
}

