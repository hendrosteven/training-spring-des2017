package com.brainmatic.controller.api;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brainmatic.dto.Result;
import com.brainmatic.entity.Category;
import com.brainmatic.repo.CategoryRepo;

@RestController("categoryApi")
@RequestMapping("/api/category")
@Transactional
public class CategoryController {

	@Autowired
	private CategoryRepo repo;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> insertOrUpdate(@Valid @RequestBody Category category, 
			BindingResult bindingResult) {
		
		Result<Category> result = new Result<Category>();
		
		if(bindingResult.hasErrors()) {
			for(ObjectError err: bindingResult.getAllErrors()) {
				result.getMessages().add(err.getDefaultMessage());
			}
			result.setStatus(0);
			return ResponseEntity.badRequest().body(result);
		}
		result.setStatus(1);
		result.setPayload(repo.save(category));
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		Result<Category> result = new Result<Category>();
		result.setStatus(1);
		result.setPayload(repo.findOne(id));
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findAll(){
		Result<List<Category>> result = new Result<List<Category>>();
		result.setStatus(1);
		result.setPayload(repo.findAll());
		return ResponseEntity.ok(result);
	}
}
