package com.drishti.shoppingcartbackend.controllers;

import com.drishti.shoppingcartbackend.entities.User;
import com.drishti.shoppingcartbackend.payloads.JwtAuthenticationRequest;
import com.drishti.shoppingcartbackend.payloads.JwtAuthenticationResponse;
import com.drishti.shoppingcartbackend.payloads.UserDto;
import com.drishti.shoppingcartbackend.security.JwtTokenHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.drishti.shoppingcartbackend.repositories.UserRepository;
import com.drishti.shoppingcartbackend.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
  @Autowired
  private JwtTokenHelper jwtTokenHelper;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;


  @Autowired
  private ModelMapper modelMapper;

  @PostMapping("/login")
  public ResponseEntity<JwtAuthenticationResponse> createToken(
          @RequestBody JwtAuthenticationRequest request
  ) throws Exception {
    this.authenticate(request.getUsername(), request.getPassword());

    UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

    String token = this.jwtTokenHelper.generateToken(userDetails);

    JwtAuthenticationResponse response = new JwtAuthenticationResponse();
    response.setToken(token);
    response.setUser(this.modelMapper.map((User) userDetails, UserDto.class));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  private void authenticate(String username, String password) throws Exception {
    UsernamePasswordAuthenticationToken authenticationToken = new
            UsernamePasswordAuthenticationToken(username, password);
    try {
      this.authenticationManager.authenticate(authenticationToken);
    } catch (BadCredentialsException e) {
      System.out.println("Invalid Details");
      throw new Exception("Invalid username or password");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
    UserDto registeredUser = this.userService.registerUser(userDto);
    return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
  }
}
