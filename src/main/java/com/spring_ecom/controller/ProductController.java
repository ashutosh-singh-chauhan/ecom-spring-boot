package com.spring_ecom.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring_ecom.model.Product;
import com.spring_ecom.service.ProductService;

@RestController 
@RequestMapping ("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
  
  @Autowired 
  private ProductService productService; 

  // ResponseEntity class is used to pass the response status to the client.
  @GetMapping ("/products")
  public ResponseEntity<List<Product>> getProducts(){
    return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
  }

  @GetMapping ("/product/{productId}")
  public ResponseEntity<Product> getProductDetail (@PathVariable int productId) { 
    Optional<Product> product = productService.getProductById(productId);
    // isPresent gives us true if value is present
    if (product.isPresent())
      return new ResponseEntity<>(product.get(), HttpStatus.OK);
    else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping ("/product")
  public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
    System.err.println("inside controller");
    Product savedProduct = null;
    try {
      savedProduct = productService.addProduct(product, imageFile);
      return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    } catch (IOException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping ("/product/{productId}/image")
  public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
    // Here .map() is a method associated with Optional<T> class
    Optional<Product> product = productService.getProductById(productId);
    return new ResponseEntity<>(product.map(Product::getImageData).orElse(null), HttpStatus.OK);
  }

  @PutMapping ("/product/{id}")
  public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) {
    Product updatedProduct = null;
    try {
      updatedProduct = productService.updatedProduct(product, imageFile);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return new ResponseEntity<>("Updated", HttpStatus.OK);
  }

  // Delete end point
  @DeleteMapping ("/product/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable int id) {
    Optional<Product> product = productService.getProductById(id);
    if (product != null) {
      productService.deleteProduct(id);
      return new ResponseEntity<>("Deleted", HttpStatus.OK);
    } else 
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
  // Search product
  @GetMapping ("/products/search")
  public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
    List<Product> products = productService.searchProducts(keyword);
    System.err.println("searching with " + keyword);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }
}