package com.yildirimbayrakci.movieapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yildirimbayrakci.movieapp.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	// Check with name if category exists.
	public boolean existsCategoryByName(String name);
	// Find category by name.
	Category findByName(String name);
	
}
