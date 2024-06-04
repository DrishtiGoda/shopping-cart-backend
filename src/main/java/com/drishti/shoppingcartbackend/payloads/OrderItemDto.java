package com.drishti.shoppingcartbackend.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

  private Long orderItemId;
  private int quantity;
  private ProductDto product;

}
