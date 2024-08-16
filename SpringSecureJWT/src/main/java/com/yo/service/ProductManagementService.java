package com.yo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yo.model.Product;
import com.yo.model.UserData;
import com.yo.repositoy.ProductRepository;
import com.yo.repositoy.UserRepository;

@Service
public class ProductManagementService {
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Integer id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());

            return productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found with id " + id);
        }
    }

    public String deleteProduct(Integer id) {
        productRepository.deleteById(id);
        return "Product deleted ID->"+id;
    }
    
    public String addUser(UserData userData) {
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        repository.save(userData);
        return "user added to system ";
    }


}
