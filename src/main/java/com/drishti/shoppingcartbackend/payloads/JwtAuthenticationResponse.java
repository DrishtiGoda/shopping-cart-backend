package com.drishti.shoppingcartbackend.payloads;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

  private String token;
  private UserDto user;

}
