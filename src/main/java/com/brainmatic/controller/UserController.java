package com.brainmatic.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brainmatic.dto.SearchName;
import com.brainmatic.entity.User;
import com.brainmatic.repo.UserRepo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepo repo;
	@Autowired
	private HttpSession session;

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String loginUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("searchName", new SearchName());
		return "user_login_page";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(User user, Model model) {
		model.addAttribute("searchName", new SearchName());

		User loginUser = repo.findByEmailAndPassword(user.getEmail(), user.getPassword());
		if (loginUser != null) {
			session.setAttribute("loginUser", loginUser);
			return "redirect:/";
		} else {
			session.setAttribute("messages", "Login fail");
			return "redirect:/user/login";
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/register")
	public String registerUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("searchName", new SearchName());
		return "user_register_page";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
		model.addAttribute("searchName", new SearchName());

		if (bindingResult.hasErrors()) {
			return "user_register_page";
		} else {
			repo.save(user);
			return "redirect:/user/login";
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/logout")
	public String logoutUser() {
		session.invalidate();
		return "redirect:/";
	}
}
