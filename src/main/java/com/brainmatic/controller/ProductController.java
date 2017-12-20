package com.brainmatic.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brainmatic.dto.SearchName;
import com.brainmatic.entity.Product;
import com.brainmatic.repo.CategoryRepo;
import com.brainmatic.repo.ProductRepo;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductRepo repo;
	@Autowired
	private CategoryRepo cRepo;
	
	@RequestMapping(method=RequestMethod.GET, value="/add")
	public String addProduct(Model model) {
		model.addAttribute("categories", cRepo.findAll());
		model.addAttribute("product", new Product());
		model.addAttribute("searchName", new SearchName());
		return "product_add_page";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String saveProduct(@Valid Product product, BindingResult bindingResult, Model model){
		model.addAttribute("categories", cRepo.findAll());
		model.addAttribute("product", new Product());
		model.addAttribute("searchName", new SearchName());
		if(bindingResult.hasErrors()) {
			return "product_add_page";
		}
		repo.save(product);
		return "redirect:/";
	}
}
