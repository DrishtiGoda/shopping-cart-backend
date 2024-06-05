package com.drishti.shoppingcartbackend.services.impl;

import com.drishti.shoppingcartbackend.entities.Product;
import com.drishti.shoppingcartbackend.exceptions.ResourceNotFoundException;
import com.drishti.shoppingcartbackend.payloads.ProductDto;
import com.drishti.shoppingcartbackend.repositories.ProductRepository;
import com.drishti.shoppingcartbackend.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public ProductDto addProduct(ProductDto productDto) {
    Product product = this.modelMapper.map(productDto, Product.class);
    Product addedProduct = this.productRepository.save(product);

    return this.modelMapper.map(addedProduct, ProductDto.class);
  }

  @Override
  public List<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

    Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<Product> pageProducts = this.productRepository.findAll(pageable);
    List<Product> products = pageProducts.getContent();

    List<ProductDto> productDtos = products.stream()
            .map(product -> this.modelMapper.map(product, ProductDto.class))
            .collect(Collectors.toList());

    return productDtos;
  }

  @Override
  public ProductDto getProductById(Long productId) {
    Product product = this.productRepository.findById(productId).
            orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

    return this.modelMapper.map(product, ProductDto.class);
  }

  @Override
  public ProductDto updateProductById(Long productId, ProductDto productDto) {
    Product product = this.productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("product", "productId", productId));

    product.setName(productDto.getName());
    product.setPrice(productDto.getPrice());
    product.setDescription(productDto.getDescription());
    Product updatedProduct = this.productRepository.save(product);
    return this.modelMapper.map(updatedProduct, ProductDto.class);
  }

  @Override
  public void deleteProductById(Long productId) {
    Product product = this.productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

    this.productRepository.delete(product);
  }

}
