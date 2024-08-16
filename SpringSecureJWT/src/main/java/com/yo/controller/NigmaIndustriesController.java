package com.yo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yo.DTO.AuthRequest;
import com.yo.model.Product;
import com.yo.model.UserData;
import com.yo.security.JwtService;
import com.yo.service.ProductManagementService;

@RestController
@RequestMapping("/Nigma")
public class NigmaIndustriesController {

	@Autowired
	private ProductManagementService service;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserData data) {
		String userData = service.addUser(data);
		return new ResponseEntity<String>(userData, HttpStatus.CREATED);

	}
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@PostMapping("/add")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {

		Product prod = service.createProduct(product);
		return new ResponseEntity<Product>(prod, HttpStatus.CREATED);

	}
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@GetMapping("/getId/{id}")
	public ResponseEntity<Optional<Product>> getProductById(@PathVariable Integer id) {
		Optional<Product> prodId = service.getProductById(id);
		return new ResponseEntity<Optional<Product>>(prodId, HttpStatus.FOUND);
	}
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@GetMapping("/getAll")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> allProducts = service.getAllProducts();

		return new ResponseEntity<List<Product>>(allProducts, HttpStatus.FOUND);
	}
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@PostMapping("/update/{id}/{productDetails}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @PathVariable Product productDetails) {
		Product updated = service.updateProduct(id, productDetails);

		return new ResponseEntity<Product>(updated, HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
		String msg = service.deleteProduct(id);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.genToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

}
