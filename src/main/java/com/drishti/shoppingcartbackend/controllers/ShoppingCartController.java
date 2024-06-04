package com.drishti.shoppingcartbackend.controllers;


import com.drishti.shoppingcartbackend.exceptions.ResourceNotFoundException;
import com.drishti.shoppingcartbackend.payloads.ApiResponse;
import com.drishti.shoppingcartbackend.payloads.CartDetailDto;
import com.drishti.shoppingcartbackend.payloads.OrderItemDto;
import com.drishti.shoppingcartbackend.services.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {

  private static final Logger log = LoggerFactory.getLogger(ShoppingCartController.class);

  @Autowired
  private ShoppingCartService shoppingCartService;

  @PostMapping("")
  public ResponseEntity<?> addItem(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
    log.info("Starting addItem method - Attempting to add item to cart for user ID: {}, product ID: {}, quantity: {}", userId, productId, quantity);

    try {
      log.debug("Adding item with user ID: {}, product ID: {}, quantity: {}", userId, productId, quantity);
      OrderItemDto addedItem = this.shoppingCartService.addItem(userId, productId, quantity);
      log.info("Successfully added item to cart for user ID: {}, product ID: {}, quantity: {}", userId, productId, quantity);
      return new ResponseEntity<>(addedItem, HttpStatus.OK);
    } catch (ResourceNotFoundException ex) {
      log.error("Resource not found in addItem method: {}", ex.getMessage());
      ApiResponse response = new ApiResponse(ex.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      log.error("Error occurred in addItem method: {}", ex.getMessage());
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("")
  public ResponseEntity<?> updateItem(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
    log.info("Starting updateItem method - Attempting to update item for user ID: {}, product ID: {}, quantity: {}", userId, productId, quantity);

    try {
      log.debug("Update payload for user ID: {}, product ID: {}, quantity: {}", userId, productId, quantity);
      OrderItemDto updatedItem = this.shoppingCartService.updateItem(userId, productId, quantity);
      log.info("Successfully updated item for user ID: {}, product ID: {}, quantity: {}", userId, productId, quantity);
      return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    } catch (ResourceNotFoundException ex) {
      log.error("Resource not found in updateItem method: {}", ex.getMessage());
      ApiResponse response = new ApiResponse(ex.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      log.error("Error occurred in updateItem method while updating item for user ID: {}, product ID: {}, quantity: {}", userId, productId, quantity, ex);
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> viewCart(@PathVariable Long userId) {
    log.info("Starting viewCart method - Attempting to view cart for user ID: {}", userId);

    try {
      CartDetailDto userCart = this.shoppingCartService.viewCart(userId);
      log.info("Successfully retrieved cart for user ID: {}", userId);
      return new ResponseEntity<>(userCart, HttpStatus.OK);
    } catch (ResourceNotFoundException ex) {
      log.error("Resource not found in viewCart method: {}", ex.getMessage());
      ApiResponse response = new ApiResponse(ex.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      log.error("Error occurred in viewCart method while viewing cart for user ID: {}", userId, ex);
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{cartId}")
  public ResponseEntity<?> deleteCart(@PathVariable Long cartId) {
    log.info("Starting deleteCart method - Attempting to delete cart with ID: {}", cartId);

    try {
      this.shoppingCartService.deleteCart(cartId);
      log.info("Successfully deleted cart with ID: {}", cartId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (ResourceNotFoundException ex) {
      log.error("Resource not found in deleteCart method: {}", ex.getMessage());
      ApiResponse response = new ApiResponse(ex.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      log.error("Error occurred in deleteCart method while deleting cart with ID: {}", cartId, ex);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/item")
  public ResponseEntity<?> deleteItemById(@RequestParam Long productId, @RequestParam Long cartId) {
    log.info("Starting deleteItemById method - Attempting to delete item with product ID: {} from cart ID: {}", productId, cartId);

    try {
      this.shoppingCartService.deleteItemById(productId, cartId);
      log.info("Successfully deleted item with product ID: {} from cart ID: {}", productId, cartId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (ResourceNotFoundException ex) {
      log.error("Resource not found in deleteItemById method: {}", ex.getMessage());
      ApiResponse response = new ApiResponse(ex.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      log.error("Error occurred in deleteItemById method while deleting item with product ID: {} from cart ID: {}", productId, cartId, ex);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
