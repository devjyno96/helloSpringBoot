package kr.ac.hansung.cse.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	List<Product> findAll();
	List<Product> findByCategory(String category);
	Product save(Product product);
	Product deleteById(int id);
}