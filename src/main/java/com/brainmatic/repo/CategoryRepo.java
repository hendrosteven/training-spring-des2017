package com.brainmatic.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.brainmatic.entity.Category;

public interface CategoryRepo extends CrudRepository<Category, Long> {

	public List<Category> findByNameIgnoringCase(String name);

	public List<Category> findAll();

	@Query("select c from Category c where c.name like :paramName")
	public List<Category> findByName(@Param("paramName") String paramName);
}
