package com.brainmatic.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brainmatic.entity.User;
import com.brainmatic.repo.UserRepo;

@Service
@Transactional
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String email) 
			throws UsernameNotFoundException {
		User user = repo.findByEmail(email);
		if(user==null){
			throw new 
			UsernameNotFoundException("No user with this email");
		}else{
			List<String> roles = new ArrayList<String>();
			roles.add("ADMIN");
			return new User(user,roles);
		}
	}
	
	public User login(String email, String password) {
		return repo.findByEmailAndPassword(email, password);
	}
	
	public User update(User user) {
		return repo.save(user);
	}

	
}
