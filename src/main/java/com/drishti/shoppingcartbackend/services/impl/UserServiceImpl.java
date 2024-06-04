package com.drishti.shoppingcartbackend.services.impl;

import com.drishti.shoppingcartbackend.entities.User;
import com.drishti.shoppingcartbackend.payloads.UserDto;
import com.drishti.shoppingcartbackend.repositories.UserRepository;
import com.drishti.shoppingcartbackend.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDto registerUser(UserDto userDto) {
    if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
      throw new IllegalArgumentException(String.format("A user with email %s already exists", userDto.getEmail()));
    }

    User user = this.modelMapper.map(userDto, User.class);
    user.setPassword(this.passwordEncoder.encode(user.getPassword()));

    User newUser = this.userRepository.save(user);
    return this.modelMapper.map(newUser, UserDto.class);
  }

}
