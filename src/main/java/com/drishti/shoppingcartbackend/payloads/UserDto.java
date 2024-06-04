package com.drishti.shoppingcartbackend.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {

  private Long id;
  private String name;
  private String email;
  private String password;

}
