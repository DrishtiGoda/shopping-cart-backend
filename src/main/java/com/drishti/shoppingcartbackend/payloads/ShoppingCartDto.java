package com.drishti.shoppingcartbackend.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShoppingCartDto {

  private Long cartId;
  private List<OrderItemDto> orderItems;
  private UserDto user;

}
