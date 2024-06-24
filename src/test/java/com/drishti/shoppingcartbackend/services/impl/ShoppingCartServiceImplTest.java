package com.drishti.shoppingcartbackend.services.impl;

import com.drishti.shoppingcartbackend.entities.OrderItem;
import com.drishti.shoppingcartbackend.entities.Product;
import com.drishti.shoppingcartbackend.entities.ShoppingCart;
import com.drishti.shoppingcartbackend.entities.User;
import com.drishti.shoppingcartbackend.payloads.CartDetailDto;
import com.drishti.shoppingcartbackend.payloads.OrderItemDto;
import com.drishti.shoppingcartbackend.repositories.OrderItemRepository;
import com.drishti.shoppingcartbackend.repositories.ShoppingCartRepository;
import com.drishti.shoppingcartbackend.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class ShoppingCartServiceImplTest {

  @Mock
  private OrderItemRepository orderItemRepository;

  @Mock
  private ShoppingCartRepository shoppingCartRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private ShoppingCartServiceImpl shoppingCartService;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }


  @Test
  public void testViewCart() {
    Long userId = 1L;
    Long cartId = 1L;
    ShoppingCart shoppingCart = new ShoppingCart();
    User user = new User(userId, "Drishti", "drishti@gmail.com", "password", shoppingCart);
    shoppingCart.setCartId(cartId);
    shoppingCart.setUser(user);
    shoppingCart.setOrderItems(new ArrayList<>());

    Product product1 = new Product(1L, "Trimmer", 100.0, "Description");
    Product product2 = new Product(2L, "Monitor", 100.0, "Description");

    OrderItem orderItem1 = new OrderItem(1L, 5, product1, shoppingCart);
    OrderItem orderItem2 = new OrderItem(2L, 10, product2, shoppingCart);

    shoppingCart.getOrderItems().add(orderItem1);
    shoppingCart.getOrderItems().add(orderItem2);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(shoppingCartRepository.findByUser(user)).thenReturn(Optional.of(shoppingCart));
    when(orderItemRepository.findByShoppingCart(shoppingCart)).thenReturn(shoppingCart.getOrderItems());

    CartDetailDto result = shoppingCartService.viewCart(userId);

    assertNotNull(result);
    assertEquals(Long.valueOf(1L), result.getCartId());
    assertEquals(15, result.getTotalItems());
    assertEquals(1500.0, result.getTotalValue(), 0.001);

    assertEquals(2, result.getItems().size());

    OrderItemDto itemDto1 = result.getItems().get(0);
    assertEquals(Long.valueOf(1L), itemDto1.getOrderItemId());
    assertEquals(5, itemDto1.getQuantity());
    assertEquals(Long.valueOf(1L), itemDto1.getProduct().getProductId());
    assertEquals("Trimmer", itemDto1.getProduct().getName());
    assertEquals(100.0, itemDto1.getProduct().getPrice(), 0.001);
    assertEquals("Description", itemDto1.getProduct().getDescription());

    OrderItemDto itemDto2 = result.getItems().get(1);
    assertEquals(Long.valueOf(2L), itemDto2.getOrderItemId());
    assertEquals(10, itemDto2.getQuantity());
    assertEquals(Long.valueOf(2L), itemDto2.getProduct().getProductId());
    assertEquals("Monitor", itemDto2.getProduct().getName());
    assertEquals(100.0, itemDto2.getProduct().getPrice(), 0.001);
    assertEquals("Description", itemDto2.getProduct().getDescription());
  }

  @Test
  public void testDeleteCart() {
    Long cartId = 1L;
    Long userId = 1L;

    ShoppingCart shoppingCart = new ShoppingCart();
    User user = new User(userId, "Drishti", "drishti@gmail.com", "password", shoppingCart);
    shoppingCart.setCartId(cartId);
    shoppingCart.setUser(user);

    when(shoppingCartRepository.findById(cartId)).thenReturn(Optional.of(shoppingCart));

    shoppingCartService.deleteCart(cartId);

    verify(userRepository, times(1)).save(user);

    verify(shoppingCartRepository, times(1)).delete(shoppingCart);
    assertNull(user.getShoppingCart());

  }


  @Test
  public void testDeleteItemById() {
    Long userId = 1L;
    Long cartId = 1L;
    Long productId1 = 1L;
    Long productId2 = 2L;
    Product product1 = new Product(productId1, "Philips Toothbrush", 100.0, "Description 1");
    Product product2 = new Product(productId2, "Philips Monitor", 300.0, "Description 2");

    ShoppingCart shoppingCart = new ShoppingCart();
    User user = new User(userId, "Drishti", "drishti@gmail.com", "password", shoppingCart);

    shoppingCart.setCartId(cartId);
    shoppingCart.setUser(user);
    shoppingCart.setOrderItems(new ArrayList<>());

    OrderItem orderItem1 = new OrderItem(1L, 3, product1, shoppingCart);
    OrderItem orderItem2 = new OrderItem(2L, 5, product2, shoppingCart);

    shoppingCart.getOrderItems().add(orderItem1);
    shoppingCart.getOrderItems().add(orderItem2);

    when(shoppingCartRepository.findById(cartId)).thenReturn(Optional.of(shoppingCart));

    shoppingCartService.deleteItemById(productId1, cartId);

    verify(shoppingCartRepository).findById(cartId);

    Assertions.assertEquals(1, shoppingCart.getOrderItems().size());
    verify(orderItemRepository).delete(orderItem1);
  }
}