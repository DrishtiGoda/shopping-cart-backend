package com.drishti.shoppingcartbackend.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailDto {

  private Long cartId;
  private int totalItems;
  private double totalValue;
  private List<OrderItemDto> items;

}
