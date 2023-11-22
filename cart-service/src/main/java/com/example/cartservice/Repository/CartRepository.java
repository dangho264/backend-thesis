package com.example.cartservice.Repository;

import com.example.cartservice.Entity.Cart;
import com.example.cartservice.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository  extends JpaRepository<Cart, String> {

}
