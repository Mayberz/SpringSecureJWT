package com.yo.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yo.model.Product;

public interface ProductRepository  extends JpaRepository<Product, Integer>{

}
