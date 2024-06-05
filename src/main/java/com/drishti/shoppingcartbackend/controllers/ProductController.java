package com.drishti.shoppingcartbackend.controllers;

import com.drishti.shoppingcartbackend.exceptions.ResourceNotFoundException;
import com.drishti.shoppingcartbackend.payloads.ApiResponse;
import com.drishti.shoppingcartbackend.payloads.ProductDto;
import com.drishti.shoppingcartbackend.services.ProductService;
import com.drishti.shoppingcartbackend.config.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

  private static final Logger log = LoggerFactory.getLogger(ProductController.class);

  @Autowired
  private ProductService productService;

  @PostMapping("")
  public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto) {
    log.info("Starting addProduct method - Attempting to add product with name: {}", productDto.getName());

    try {
      log.debug("Request payload: {}", productDto);
      ProductDto createdProduct = this.productService.addProduct(productDto);
      log.info("Successfully created product with ID: {} and name: {}", createdProduct.getProductId(), createdProduct.getName());

      return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    } catch (Exception ex) {
      log.error("Error occurred in addProduct method while adding product with name: {}", productDto.getName(), ex);

      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @GetMapping("")
  public ResponseEntity<List<ProductDto>> getAllProducts(
          @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
          @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
          @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
          @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection) {

    log.info("Starting getAllProducts method - Attempting to retrieve all products");

    try {
      List<ProductDto> allProducts = this.productService.getAllProducts(pageNumber, pageSize, sortBy, sortDirection);
      log.info("Successfully retrieved {} products", allProducts.size());

      return new ResponseEntity<>(allProducts, HttpStatus.OK);
    } catch (Exception ex) {
      log.error("Error occurred in getAllProducts method while retrieving products", ex);

      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @GetMapping("/{productId}")
  public ResponseEntity<?> getProductById(@PathVariable Long productId) {
    log.info("Starting getProductById method - Attempting to retrieve product with ID: {}", productId);

    try {
      ProductDto product = this.productService.getProductById(productId);
      if (product != null) {
        log.info("Successfully retrieved product with ID: {} and name: {}", productId, product.getName());
        log.debug("Retrieved product details: {}", product);

        return new ResponseEntity<>(product, HttpStatus.OK);
      } else {
        log.warn("No product found with ID: {}", productId);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
    } catch (ResourceNotFoundException ex) {
      log.error("Resource not found in getProductById method: {}", ex.getMessage());
      ApiResponse response = new ApiResponse(ex.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      log.error("Error occurred in getProductById method while retrieving product with ID: {}", productId, ex);

      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @PutMapping("/{productId}")
  public ResponseEntity<?> updateProductById(@Valid @PathVariable Long productId, @RequestBody ProductDto productDto) {
    log.info("Starting updateProductById method - Attempting to update product with ID: {}", productId);

    try {
      log.debug("Update payload for product ID {}: {}", productId, productDto);
      ProductDto updatedProduct = this.productService.updateProductById(productId, productDto);
      log.info("Successfully updated product with ID: {} and name: {}", productId, updatedProduct.getName());

      return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    } catch (ResourceNotFoundException ex) {
      log.error("Resource not found in updateProductById method: {}", ex.getMessage());
      ApiResponse response = new ApiResponse(ex.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      log.error("Error occurred in updateProductById method while updating product with ID: {}", productId, ex);

      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @DeleteMapping("/{productId}")
  public ResponseEntity<?> deleteProductById(@PathVariable Long productId) {
    log.info("Starting deleteProductById method - Attempting to delete product with ID: {}", productId);

    try {
      this.productService.deleteProductById(productId);
      log.info("Successfully deleted product with ID: {}", productId);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (ResourceNotFoundException ex) {
      log.error("Resource not found in deleteProductById method: {}", ex.getMessage());
      ApiResponse response = new ApiResponse(ex.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      log.error("Error occurred in deleteProductById method while deleting product with ID: {}", productId, ex);

      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
