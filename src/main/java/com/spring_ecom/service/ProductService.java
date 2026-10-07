package com.spring_ecom.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring_ecom.model.Product;
import com.spring_ecom.repository.ProductRepo;

@Service 
public class ProductService {

  @Autowired 
  private ProductRepo productRepo;

  public List<Product> findAllProducts() {
    return productRepo.findAll();
  }

  public Optional<Product> getProductById(int id) {
    return productRepo.findById(id);
  }

  public Product addProduct(Product product, MultipartFile file) throws IOException {
    product.setImageName(file.getOriginalFilename());
    product.setImageType(file.getContentType());
    product.setImageData(file.getBytes());
    return productRepo.save(product);
  }

  public Product updatedProduct(Product product, MultipartFile image) throws IOException {
    // TODO Auto-generated method stub
    product.setImageName(image.getOriginalFilename());
    product.setImageType(image.getContentType());
    product.setImageData(image.getBytes());
    return productRepo.save(product);
  }

  public void deleteProduct(int id) {
    // TODO Auto-generated method stub
    productRepo.deleteById(id);
  }

  public List<Product> searchProducts(String keyword) {
    // TODO Auto-generated method stub
    return productRepo.searchProducts(keyword);
  }


}
