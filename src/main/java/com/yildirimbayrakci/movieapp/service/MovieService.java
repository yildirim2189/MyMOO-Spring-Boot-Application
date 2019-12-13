package com.yildirimbayrakci.movieapp.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yildirimbayrakci.movieapp.entity.Actor;
import com.yildirimbayrakci.movieapp.entity.Category;
import com.yildirimbayrakci.movieapp.entity.Movie;

public interface MovieService {
	
	// Find all entities
	public List<Movie> findAllMovies();
	public List<Category> findAllCategories();
	public List<Actor> findAllActors();
	public Page<Movie> findAllMovies(Pageable pageable);
	
	// Save Entities
	public void saveActor(Actor actor);
	public Movie saveMovie(Movie movie);
	public void saveCategory(Category category);
	
	// Various methods for fetch entities.
	public Movie findMovieById(int movieId);
	public Category findCategoryById(int categoryId);
	public Actor findActorById(int actorId);
	public Actor findActorByName(String name);
	public void deleteMovieById(int id);
	public Page<Movie> findMovieByTitleContaining(String movieTitle, Pageable pageable);
	public Page<Movie> findMoviesByCategoryAndTitleContaining(int categoryId, String title, Pageable pageable);
	public Page<Movie> findMoviesByActorName(String query, Pageable pageable);
	public Page<Movie> findMoviesByCategoryAndActorName(int categoryId, String query, Pageable pageable);
	public Page<Movie> findMoviesByUserFavoritedBy(Long userId, Pageable pageable);
	public void deleteCategory(int categoryId);
	public Set<Movie> findMovieByCategory(int categoryId);
	
}
