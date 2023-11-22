package com.example.cartservice.Repository;

import com.example.cartservice.Entity.Cart;
import com.example.cartservice.Entity.CartItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository

public interface CartItemRepository extends CrudRepository<CartItem, Long> {


    @Transactional
    void deleteByCartIdAndProductId(Long cartId, Long productId);

    @Transactional
    void deleteByCartIdAndProductIdIn(Long cartId, List<Long> productIds);
    public CartItem findCartItemByProductId(int productId);

//    @Query("select sum(ci.quantity) from CartItem ci where ci.cart.id = ?1")
//    Long countItemInCart(Long cartId);
}
