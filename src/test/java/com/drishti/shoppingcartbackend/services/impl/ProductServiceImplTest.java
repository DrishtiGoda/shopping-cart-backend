package com.drishti.shoppingcartbackend.services.impl;

import com.drishti.shoppingcartbackend.entities.Product;
import com.drishti.shoppingcartbackend.exceptions.ResourceNotFoundException;
import com.drishti.shoppingcartbackend.payloads.ProductDto;
import com.drishti.shoppingcartbackend.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {
  @Mock
  private ProductRepository productRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private ProductServiceImpl productService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testAddProduct() {
    // Given
    ProductDto productDto = new ProductDto(null, "Test Product", 10.0, "Test Description");
    Product product = new Product();
    product.setName("Test Product");
    product.setPrice(10.0);
    product.setDescription("Test Description");

    Product savedProduct = new Product();
    savedProduct.setProductId(1L);
    savedProduct.setName("Test Product");
    savedProduct.setPrice(10.0);
    savedProduct.setDescription("Test Description");

    when(modelMapper.map(any(ProductDto.class), eq(Product.class))).thenReturn(product);
    when(modelMapper.map(any(Product.class), eq(ProductDto.class))).thenReturn(new ProductDto(1L, "Test Product", 10.0, "Test Description"));

    when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

    // When
    ProductDto result = productService.addProduct(productDto);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.getProductId());
    assertEquals("Test Product", result.getName());
    assertEquals(10.0, result.getPrice());
    assertEquals("Test Description", result.getDescription());
  }

  @Test
  void testGetAllProducts() {
    // Given
    Product product1 = new Product(1L, "Product 1", 10.0, "Description 1");
    Product product2 = new Product(2L, "Product 2", 20.0, "Description 2");
    List<Product> products = Arrays.asList(product1, product2);

    when(productRepository.findAll()).thenReturn(products);
    when(modelMapper.map(product1, ProductDto.class)).thenReturn(new ProductDto(1L, "Product 1", 10.0, "Description 1"));
    when(modelMapper.map(product2, ProductDto.class)).thenReturn(new ProductDto(2L, "Product 2", 20.0, "Description 2"));

    // When
    List<ProductDto> result = productService.getAllProducts();

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());

    ProductDto productDto1 = result.get(0);
    assertEquals(1L, productDto1.getProductId());
    assertEquals("Product 1", productDto1.getName());
    assertEquals(10.0, productDto1.getPrice());
    assertEquals("Description 1", productDto1.getDescription());

    ProductDto productDto2 = result.get(1);
    assertEquals(2L, productDto2.getProductId());
    assertEquals("Product 2", productDto2.getName());
    assertEquals(20.0, productDto2.getPrice());
    assertEquals("Description 2", productDto2.getDescription());
  }

  @Test
  void testGetProductById() {
    // Given
    Long productId = 1L;
    Product product = new Product(productId, "Test Product", 10.0, "Test Description");

    when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));
    when(modelMapper.map(product, ProductDto.class)).thenReturn(new ProductDto(productId, "Test Product", 10.0, "Test Description"));

    // When
    ProductDto result = productService.getProductById(productId);

    // Then
    assertNotNull(result);
    assertEquals(productId, result.getProductId());
    assertEquals("Test Product", result.getName());
    assertEquals(10.0, result.getPrice());
    assertEquals("Test Description", result.getDescription());
  }

  @Test
  void testGetProductById_NotFound() {
    // Given
    Long productId = 1L;

    when(productRepository.findById(productId)).thenReturn(java.util.Optional.empty());

    // When / Then
    assertThrows(ResourceNotFoundException.class, () -> {
      productService.getProductById(productId);
    });
  }

  @Test
  void testUpdateProductById() {
    // Given
    Long productId = 1L;
    ProductDto productDto = new ProductDto(null, "Updated Product", 15.0, "Updated Description");
    Product product = new Product(productId, "Test Product", 10.0, "Test Description");
    Product updatedProduct = new Product(productId, "Updated Product", 15.0, "Updated Description");

    when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));
    when(productRepository.save(product)).thenReturn(updatedProduct);
    when(modelMapper.map(updatedProduct, ProductDto.class)).thenReturn(new ProductDto(productId, "Updated Product", 15.0, "Updated Description"));

    // When
    ProductDto result = productService.updateProductById(productId, productDto);

    // Then
    assertNotNull(result);
    assertEquals(productId, result.getProductId());
    assertEquals("Updated Product", result.getName());
    assertEquals(15.0, result.getPrice());
    assertEquals("Updated Description", result.getDescription());
  }

  @Test
  void testUpdateProductById_NotFound() {
    // Given
    Long productId = 1L;
    ProductDto productDto = new ProductDto(null, "Updated Product", 15.0, "Updated Description");

    when(productRepository.findById(productId)).thenReturn(java.util.Optional.empty());

    // When / Then
    assertThrows(ResourceNotFoundException.class, () -> {
      productService.updateProductById(productId, productDto);
    });
  }

  @Test
  void testDeleteProductById() {
    // Given
    Long productId = 1L;
    Product product = new Product(productId, "Test Product", 10.0, "Test Description");

    when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));

    // When
    productService.deleteProductById(productId);

    // Then
    verify(productRepository, times(1)).delete(product);
  }

  @Test
  void testDeleteProductById_NotFound() {
    // Given
    Long productId = 1L;

    when(productRepository.findById(productId)).thenReturn(java.util.Optional.empty());

    // When / Then
    assertThrows(ResourceNotFoundException.class, () -> {
      productService.deleteProductById(productId);
    });
  }
}
