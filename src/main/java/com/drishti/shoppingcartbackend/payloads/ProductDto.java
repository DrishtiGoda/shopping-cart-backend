package com.drishti.shoppingcartbackend.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

  private Long productId;
  private String name;
  private double price;
  private String description;

}
