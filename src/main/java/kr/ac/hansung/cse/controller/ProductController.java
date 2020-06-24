package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.repo.ProductRepository;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/vi")
public class ProductController {

	@Autowired
	ProductRepository repository;

	@PostMapping(value = "/Products")
	public ResponseEntity<Product> postProduct(@RequestBody Product product) {
		try {
			Product _Product = repository.save(new Product(
					product.getName(), product.getCategory(), product.getPrice(), 
					product.getUnitInStock(), product.getDescription(), product.getManufacturer()
					));
			return new ResponseEntity<>(_Product, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping("/Products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> Products = new ArrayList<>();
		try {
			repository.findAll().forEach(Products::add);

			if (Products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(Products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/Products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
		Optional<Product> ProductData = Optional.of(repository.findById(id));

		if (ProductData.isPresent()) {
			return new ResponseEntity<>(ProductData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "Products/category/{category}")
	public ResponseEntity<List<Product>> findByAge(@PathVariable String category) {
		try {
			List<Product> Products = repository.findByCategory(category);

			if (Products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(Products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PutMapping("/Products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @RequestBody Product Product) {
		Optional<Product> ProductData = Optional.of(repository.findById(id));

		if (ProductData.isPresent()) {
			Product _Product = ProductData.get();
			_Product.setName(Product.getName());
			_Product.setCategory(Product.getCategory());
			_Product.setPrice(Product.getPrice());
			_Product.setUnitInStock(Product.getUnitInStock());
			_Product.setDescription(Product.getDescription());
			
			return new ResponseEntity<>(repository.save(_Product), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/Products/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") int id) {
		try {
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/Products")
	public ResponseEntity<HttpStatus> deleteAllProducts() {
		try {
			repository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}
	

}