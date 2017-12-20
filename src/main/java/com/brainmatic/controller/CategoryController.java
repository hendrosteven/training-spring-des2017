package com.brainmatic.controller;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brainmatic.entity.Category;
import com.brainmatic.repo.CategoryRepo;

@Controller
@RequestMapping("/category")
@Transactional
public class CategoryController {

	@Autowired
	private CategoryRepo repo;
	
	@RequestMapping(method=RequestMethod.GET)
	public String mainCategory(Model model) {
		model.addAttribute("categories", repo.findAll());
		return "category_main_page";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/add")
	public String addCategory(Model model) {
		model.addAttribute("category", new Category());
		return "category_add_page";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String saveCategory(Category category){
		repo.save(category);
		return "redirect:/category";
	}
	
}
