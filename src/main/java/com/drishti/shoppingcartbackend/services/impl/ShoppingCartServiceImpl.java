package com.drishti.shoppingcartbackend.services.impl;

import com.drishti.shoppingcartbackend.payloads.CartDetailDto;
import com.drishti.shoppingcartbackend.payloads.OrderItemDto;
import com.drishti.shoppingcartbackend.services.ShoppingCartService;

public class ShoppingCartServiceImpl implements ShoppingCartService {

  @Override
  public OrderItemDto addItem(Long userId, Long productId, int quantity) {
    return null;
  }

  @Override
  public OrderItemDto updateItem(Long userId, Long productId, int quantity) {
    return null;
  }

  @Override
  public CartDetailDto viewCart(Long userId) {
    return null;
  }

  @Override
  public void deleteCart(Long cartId) {

  }

  @Override
  public void deleteItemById(Long productId, Long cartId) {

  }

}
