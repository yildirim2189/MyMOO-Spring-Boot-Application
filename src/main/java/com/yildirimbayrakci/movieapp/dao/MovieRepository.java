package com.yildirimbayrakci.movieapp.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yildirimbayrakci.movieapp.entity.Actor;
import com.yildirimbayrakci.movieapp.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>{
	
	// Find movies by title containing. 
	Page<Movie> findByTitleContaining(String title, Pageable pageable);
	// Find movies with given actor objects. Return distinct result. 
	Page<Movie> findDistinctByActorsIn(List<Actor> actors, Pageable pageable);
	// Find movies with given category Ids and title containing.
	Page<Movie> findByCategories_IdInAndTitleContaining(List<Integer> categoryIds,String title, Pageable pageable);
	// Find movies with given category Ids and actor objects.
	Page<Movie> findDistinctByCategories_IdInAndActorsIn(List<Integer> categoryIds, List<Actor> actors, Pageable pageable);
	// Find movies favorited by given user Ids 
	Page<Movie> findByUsersFavoritedBy_IdIn(List<Long> usersIds, Pageable pageable);
	// Check if movie exists by title
	boolean existsMovieByTitle(String Title);
	// Find movies by category
	Set<Movie> findByCategories_IdIn(List<Integer> categoryIds);
}
