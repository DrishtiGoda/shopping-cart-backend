package com.drishti.shoppingcartbackend.repositories;

import com.drishti.shoppingcartbackend.entities.ShoppingCart;
import com.drishti.shoppingcartbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

  Optional<ShoppingCart> findByUser(User user);

}
