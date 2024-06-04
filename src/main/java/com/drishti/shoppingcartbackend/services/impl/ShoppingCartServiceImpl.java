package com.drishti.shoppingcartbackend.services.impl;

import com.drishti.shoppingcartbackend.entities.OrderItem;
import com.drishti.shoppingcartbackend.entities.Product;
import com.drishti.shoppingcartbackend.entities.ShoppingCart;
import com.drishti.shoppingcartbackend.entities.User;
import com.drishti.shoppingcartbackend.exceptions.ResourceNotFoundException;
import com.drishti.shoppingcartbackend.payloads.CartDetailDto;
import com.drishti.shoppingcartbackend.payloads.OrderItemDto;
import com.drishti.shoppingcartbackend.payloads.ProductDto;
import com.drishti.shoppingcartbackend.services.ShoppingCartService;
import com.drishti.shoppingcartbackend.repositories.OrderItemRepository;
import com.drishti.shoppingcartbackend.repositories.ProductRepository;
import com.drishti.shoppingcartbackend.repositories.ShoppingCartRepository;
import com.drishti.shoppingcartbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ShoppingCartRepository shoppingCartRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Override
  public OrderItemDto addItem(Long userId, Long productId, int quantity) {
    User user = this.userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

    Product product = this.productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

    ShoppingCart shoppingCart = this.shoppingCartRepository.findByUser(user)
            .orElseGet(() -> {
              ShoppingCart newShoppingCart = new ShoppingCart();
              newShoppingCart.setUser(user);
              return this.shoppingCartRepository.save(newShoppingCart);
            });

    OrderItem orderItem = new OrderItem();
    orderItem.setProduct(product);
    orderItem.setQuantity(quantity);
    orderItem.setShoppingCart(shoppingCart);

    orderItem = this.orderItemRepository.save(orderItem);

    return new OrderItemDto(orderItem.getOrderItemId(), orderItem.getQuantity(), new ProductDto(product.getProductId(), product.getName(), product.getPrice(), product.getDescription()));
  }

  @Override
  public OrderItemDto updateItem(Long userId, Long productId, int quantity) {
    User user = this.userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

    ShoppingCart shoppingCart = this.shoppingCartRepository.findByUser(user)
            .orElseThrow(() -> new ResourceNotFoundException("Shopping Cart", "userId", userId));

    OrderItem orderItem = orderItemRepository.findByShoppingCartAndProduct(shoppingCart, this.productRepository.findById(productId));

    orderItem.setQuantity(quantity);
    orderItem = this.orderItemRepository.save(orderItem);

    return new OrderItemDto(orderItem.getOrderItemId(), orderItem.getQuantity(), new ProductDto(orderItem.getProduct().getProductId(), orderItem.getProduct().getName(), orderItem.getProduct().getPrice(), orderItem.getProduct().getDescription()));
  }

  @Override
  public CartDetailDto viewCart(Long userId) {
    User user = this.userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

    ShoppingCart shoppingCart = this.shoppingCartRepository.findByUser(user)
            .orElseThrow(() -> new ResourceNotFoundException("Shopping Cart", "userId", userId));

    List<OrderItemDto> items = new ArrayList<>();

    List<OrderItem> orderItems = this.orderItemRepository.findByShoppingCart(shoppingCart);

    for (OrderItem orderItem : orderItems) {
      ProductDto productDto = new ProductDto(orderItem.getProduct().getProductId(),
              orderItem.getProduct().getName(),
              orderItem.getProduct().getPrice(),
              orderItem.getProduct().getDescription());

      OrderItemDto orderItemDto = new OrderItemDto(orderItem.getOrderItemId(),
              orderItem.getQuantity(),
              productDto);

      items.add(orderItemDto);
    }

    double totalValue = items.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
    int totalItems = items.stream().mapToInt(OrderItemDto::getQuantity).sum();

    return new CartDetailDto(shoppingCart.getCartId(), totalItems, totalValue, items);
  }

  @Override
  public void deleteCart(Long cartId) {
    ShoppingCart shoppingCart = this.shoppingCartRepository.findById(cartId)
            .orElseThrow(() -> new ResourceNotFoundException("Shopping Cart", "cartId", cartId));

    User user = shoppingCart.getUser();
    if (user != null) {
      user.setShoppingCart(null);
      this.userRepository.save(user);
    }

    this.shoppingCartRepository.delete(shoppingCart);
  }

  @Override
  public void deleteItemById(Long productId, Long cartId) {
    ShoppingCart shoppingCart = this.shoppingCartRepository.findById(cartId)
            .orElseThrow(() -> new ResourceNotFoundException("Shopping Cart", "cartId", cartId));

    OrderItem orderItem = shoppingCart.getOrderItems().stream()
            .filter(item -> item.getProduct().getProductId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Order Item", "productId", productId));

    shoppingCart.getOrderItems().remove(orderItem);
    this.orderItemRepository.delete(orderItem);
  }

}
