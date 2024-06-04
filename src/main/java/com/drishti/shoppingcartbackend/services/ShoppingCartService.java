package com.drishti.shoppingcartbackend.services;

import com.drishti.shoppingcartbackend.payloads.CartDetailDto;
import com.drishti.shoppingcartbackend.payloads.OrderItemDto;

public interface ShoppingCartService {

  OrderItemDto addItem(Long userId, Long productId, int quantity);

  OrderItemDto updateItem(Long userId, Long productId, int quantity);

  CartDetailDto viewCart(Long userId);

  void deleteCart(Long cartId);

  void deleteItemById(Long productId, Long cartId);

}
