package com.brainmatic.controller.api;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brainmatic.dto.Result;
import com.brainmatic.dto.SearchName;
import com.brainmatic.entity.Product;
import com.brainmatic.repo.CategoryRepo;
import com.brainmatic.repo.ProductRepo;

@CrossOrigin
@RestController("productApi")
@RequestMapping("/api/product")
@Transactional
public class ProductController {

	@Autowired
	private ProductRepo repo;
	@Autowired
	private CategoryRepo cRepo;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> insertOrUpdate(@Valid @RequestBody Product product, BindingResult bindingResult) {
		Result<Product> result = new Result<Product>();
		if(bindingResult.hasErrors()) {
			for(ObjectError err: bindingResult.getAllErrors()) {
				result.getMessages().add(err.getDefaultMessage());
			}
			result.setStatus(0);
			return ResponseEntity.badRequest().body(result);
		}
		result.setStatus(1);
		result.setPayload(repo.save(product));
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findAll(){
		Result<List<Product>> result = new Result<List<Product>>();
		result.setStatus(1);
		result.setPayload(repo.findAll());
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		Result<Product> result = new Result<Product>();
		result.setStatus(1);
		result.setPayload(repo.findOne(id));
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/search")
	public ResponseEntity<?> findByName(@RequestBody SearchName searchName){
		Result<List<Product>> result = new Result<List<Product>>();
		result.setStatus(1);
		result.setPayload(repo.findByName("%"+searchName.getName()+"%"));
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/search/category/{id}")
	public ResponseEntity<?> findByCategory(@PathVariable("id") Long id) {
		Result<List<Product>> result = new Result<List<Product>>();
		result.setStatus(1);
		result.setPayload(repo.findByCategory(cRepo.findOne(id)));
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/search/price/{min}/{max}")
	public ResponseEntity<?> findByPrice(@PathVariable("min") double min,
			@PathVariable("max") double max) {
		Result<List<Product>> result = new Result<List<Product>>();
		result.setStatus(1);
		result.setPayload(repo.findByPriceRange(min, max));
		return ResponseEntity.ok(result);
	}
}
