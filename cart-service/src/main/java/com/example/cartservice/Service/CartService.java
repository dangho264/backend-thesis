package com.example.cartservice.Service;

import com.example.cartservice.DTO.*;
import com.example.cartservice.Entity.Cart;
import com.example.cartservice.Entity.CartItem;
import com.example.cartservice.Repository.CartItemRepository;
import com.example.cartservice.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public Optional<Cart> getCartByUserId(String userId) throws ExecutionException, InterruptedException {
        Optional<Cart> carts = cartRepository.findById(userId);
        return carts;
    }

    public Optional<Cart> getCartById(String username) throws ExecutionException, InterruptedException {
        Optional<Cart> carts = cartRepository.findById(username);
        return carts;
    }
    public void setCartNotSelected(String username){
        Optional<Cart> existingCart = cartRepository.findById(username);
        existingCart.ifPresent(cart -> {
            for (CartItem cartItem : cart.getCartItems()) {
                if (cartItem.isSelected()) {
                    cartItem.setSelected(!cartItem.isSelected());
                    cartItemRepository.save(cartItem);
                    cart.setTotalPrice(BigDecimal.ZERO);
                    cartRepository.save(cart);
                }
            }
        });
    }
    public Cart getCart(String username) throws ExecutionException, InterruptedException {
        Optional<Cart> existingCart = cartRepository.findById(username);
//        existingCart.ifPresent(cart -> {
//            for (CartItem cartItem : cart.getCartItems()) {
//                if (cartItem.isSelected()) {
//                    cartItem.setSelected(!cartItem.isSelected());
//                    cartItemRepository.save(cartItem);
//                    cart.setTotalPrice(BigDecimal.ZERO);
//                    cartRepository.save(cart);
//                }
//            }
//        });
        return existingCart.isPresent() && existingCart.get() != null ? existingCart.get() : null;
    }
    public Cart getCartSelected(String username) throws ExecutionException, InterruptedException {
        Optional<Cart> existingCart = cartRepository.findById(username);
        Cart cartSelected = new Cart();
        Set<CartItem> cartItems = new HashSet<>();

        existingCart.ifPresent(cart -> {
            for (CartItem cartItem : cart.getCartItems()) {
                if (cartItem.isSelected()) {
                    cartItems.add(cartItem);
                }
            }
        });
        cartSelected.setCartItems(cartItems);
        cartSelected.setTotalPrice(existingCart.get().getTotalPrice());
        return cartSelected;
    }
    public CartResponseDTO getCartBySeller(String username) throws ExecutionException, InterruptedException {
        Optional<Cart> existingCart = cartRepository.findById(username);

        if (existingCart.isPresent() && existingCart.get() != null) {
            Cart cart = existingCart.get();
            CartResponseDTO cartResponseDTO = new CartResponseDTO();
            cartResponseDTO.setUsername(cart.getId());
            cartResponseDTO.setTotalPrice(cart.getTotalPrice());

            // Gom theo seller
            Map<String, List<CartItemResponseDTO>> cartItemsGroupedBySeller = cart.getCartItems()
                    .stream()
                    .map(cartItem -> CartItemResponseDTO.builder()
                            .id(cartItem.getId())
                            .productId(cartItem.getProductId())
                            .price(cartItem.getPrice())
                            .image_url(cartItem.getImage_url())
                            .name(cartItem.getName())
                            .quantity(cartItem.getQuantity())
                            .sellerName(cartItem.getSellername()) // Sử dụng trực tiếp sellerName từ CartItem
                            .build())
                    .collect(Collectors.groupingBy(CartItemResponseDTO::getSellerName));

            List<CartItemGroupedBySellerDTO> cartItemsBySeller = cartItemsGroupedBySeller.entrySet()
                    .stream()
                    .map(entry -> new CartItemGroupedBySellerDTO(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());

            cartResponseDTO.setCartItemsBySeller(cartItemsBySeller);

            return cartResponseDTO;
        }

        return null;
    }


    // Phương thức này cần được thay thế bằng cách bạn trích xuất sellerName từ CartItem
    private String getSellerNameFromCartItem(CartItem cartItem) {
        // Thay thế bằng cách trích xuất sellerName từ CartItem của bạn
        // Ví dụ: return cartItem.getSellerName();
        return "";
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
        if (existingCart.get().getCartItems() == null || existingCart.get().getCartItems().isEmpty()) {
            cartItems.add(new CartItem(cartItems.size()+1, req.getProductId(), req.getPrice(), req.getImage_url(), req.getName(), 1, req.getSellerName(), req.isSelected()));
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
                cartItems.add(new CartItem(cartItems.size()+1, req.getProductId(), req.getPrice(), req.getImage_url(), req.getName(), 1, req.getSellerName(), req.isSelected()));
            }

        }
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item :
                cartItems) {
            if (item.isSelected()) {
                total = total.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
            }
        }
        existingCart.get().setTotalPrice(total);
        existingCart.get().setCartItems(cartItems);
        cartRepository.save(existingCart.get());
    }

    public void selectedItemInCart(CartUpdateDTO req) {
        Optional<Cart> existingCart = cartRepository.findById(req.getUsername());

        existingCart.ifPresent(cart -> {
            for (CartItem cartItem : cart.getCartItems()) {
                if (cartItem.getProductId() == req.getProductId()) {
                    cartItem.setSelected(!cartItem.isSelected());
                    cartItemRepository.save(cartItem);
                    BigDecimal total = cart.getTotalPrice();
                    if (cartItem.isSelected()) {
                        total = total.add(cartItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
                    }
                    else{
                        total = total.subtract(cartItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
                    }
                    cart.setTotalPrice(total);
                    cartRepository.save(existingCart.get());
                    break; // Thoát sau khi tìm thấy sản phẩm
                }
            }
        });
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
        CartItem CartItem = findCartItemById(CartItems, id);
        if (CartItem != null) {
            CartItems.remove(CartItem);
        } else {
            throw new AppException("Sản phẩm không có trong giỏ hàng", HttpStatus.NOT_FOUND);
        }
        existingCart.get().setCartItems(CartItems);
        existingCart.get().setTotalPrice(existingCart.get().getTotalPrice().subtract(CartItem.getPrice().multiply(new BigDecimal(CartItem.getQuantity()))));
        cartRepository.save(existingCart.get());
        return String.format("%s đã được xóa", name);
    }

    @KafkaListener(topics = "cart-to-order", containerFactory = "kafkaListenerContainerFactory")
    public String printProduct(String username) {
        delete(username);
        System.out.println(username);
        return username;
    }
}

