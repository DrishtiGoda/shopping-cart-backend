package com.drishti.shoppingcartbackend.services;

import com.drishti.shoppingcartbackend.payloads.ProductDto;

import java.util.List;

public interface ProductService {

  ProductDto addProduct(ProductDto productDto);

  List<ProductDto> getAllProducts();

  ProductDto getProductById(Long productId);

  ProductDto updateProductById(Long productId, ProductDto productDto);

  void deleteProductById(Long productId);

}
