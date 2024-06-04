package com.drishti.shoppingcartbackend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShoppingCart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cartId;

  @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private User user;
}
