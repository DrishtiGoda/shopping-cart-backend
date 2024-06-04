package com.drishti.shoppingcartbackend.repositories;

import com.drishti.shoppingcartbackend.entities.OrderItem;
import com.drishti.shoppingcartbackend.entities.Product;
import com.drishti.shoppingcartbackend.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

  OrderItem findByShoppingCartAndProduct(ShoppingCart shoppingCart, Optional<Product> product);

  List<OrderItem> findByShoppingCart(ShoppingCart shoppingCart);

}
