package com.brainmatic.repo;

import org.springframework.data.repository.CrudRepository;

import com.brainmatic.entity.User;

public interface UserRepo extends CrudRepository<User, Long>{

	public User findByEmailAndPassword(String email, String password);
	public User findByEmail(String email);
	
}
