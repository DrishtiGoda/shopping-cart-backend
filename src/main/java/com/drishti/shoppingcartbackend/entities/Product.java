package com.drishti.shoppingcartbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @NotBlank(message = "Product name must not be empty")
  @Size(max = 100, message = "Product name must be less than 100 characters")
  private String name;

  @Min(value = 0, message = "Product price must be positive")
  private double price;

  @Size(max = 500, message = "Product description must be less than 500 characters")
  private String description;
}
