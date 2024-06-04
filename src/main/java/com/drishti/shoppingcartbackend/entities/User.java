package com.drishti.shoppingcartbackend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name must not be empty")
  @Size(max = 100, message = "Name must be less than 100 characters")
  private String name;

  @NotBlank(message = "Email must not be empty")
  @Email(message = "Email should be valid")
  @Size(max = 100, message = "Email must be less than 100 characters")
  private String email;

  @NotBlank(message = "Password must not be empty")
  @Size(min = 5, message = "Password must be at least 5 characters long")
  private String password;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
  private ShoppingCart shoppingCart;
}
