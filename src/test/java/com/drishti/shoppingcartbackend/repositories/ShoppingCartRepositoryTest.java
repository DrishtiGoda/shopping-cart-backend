package com.drishti.shoppingcartbackend.repositories;

import com.drishti.shoppingcartbackend.entities.ShoppingCart;
import com.drishti.shoppingcartbackend.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartRepositoryTest {
  @Mock
  private ShoppingCartRepository shoppingCartRepository;

  @Test
  public void testFindByUser() {
    User user = new User();
    user.setId(1L);

    ShoppingCart shoppingCart = new ShoppingCart();
    shoppingCart.setCartId(1L);
    shoppingCart.setUser(user);

    when(shoppingCartRepository.findByUser(user)).thenReturn(Optional.of(shoppingCart));

    Optional<ShoppingCart> foundCart = shoppingCartRepository.findByUser(user);

    assert (foundCart.isPresent());
    assert (foundCart.get().equals(shoppingCart));

    verify(shoppingCartRepository, times(1)).findByUser(user);
  }
}
