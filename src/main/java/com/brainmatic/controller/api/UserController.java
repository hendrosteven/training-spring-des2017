package com.brainmatic.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brainmatic.dto.Result;
import com.brainmatic.dto.SearchUser;
import com.brainmatic.entity.User;
import com.brainmatic.repo.UserRepo;

@RestController("userApi")
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserRepo repo;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> inserOrUpdate(@Valid @RequestBody User user, BindingResult bindingResult) {
		Result<User> result = new Result<User>();
		if(bindingResult.hasErrors()) {
			for(ObjectError err: bindingResult.getAllErrors()) {
				result.getMessages().add(err.getDefaultMessage());
			}
			result.setStatus(0);
			return ResponseEntity.badRequest().body(result);
		}
		result.setStatus(1);
		result.setPayload(repo.save(user));
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/email")
	public ResponseEntity<?> findByEmail(@RequestBody SearchUser searchUser) {
		Result<User> result = new Result<User>();
		result.setStatus(1);
		result.setPayload(repo.findByEmail(searchUser.getEmail()));
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/login")
	public ResponseEntity<?> findByEmailAndPassword(@RequestBody SearchUser searchUser) {
		Result<User> result = new Result<User>();
		result.setStatus(1);
		result.setPayload(repo.findByEmailAndPassword(searchUser.getEmail(),
				searchUser.getPassword()));
	    return ResponseEntity.ok(result);
	}
}
