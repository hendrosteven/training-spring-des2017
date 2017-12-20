package com.brainmatic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brainmatic.dto.SearchName;
import com.brainmatic.repo.CategoryRepo;
import com.brainmatic.repo.ProductRepo;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private CategoryRepo repo;	
	
	@Autowired
	private ProductRepo pRepo;

	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute("categories", repo.findAll());
		model.addAttribute("products", pRepo.findAll());
		model.addAttribute("searchName", new SearchName());
		return "index";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="product/{id}")
	public String findByCategory(@PathVariable("id") Long id, Model model){
		model.addAttribute("categories", repo.findAll());
		model.addAttribute("products", pRepo.findByCategory(repo.findOne(id)));
		model.addAttribute("searchName", new SearchName());
		return "index";
	}
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String findByName(SearchName searchName, Model model){
		model.addAttribute("categories", repo.findAll());
		model.addAttribute("products", pRepo.findByName("%"+searchName.getName()+"%"));
		model.addAttribute("searchName", new SearchName());
		model.addAttribute("searchText", searchName.getName());
		return "index";
	}
	
	
}
