package com.yildirimbayrakci.movieapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yildirimbayrakci.movieapp.dao.ActorRepository;
import com.yildirimbayrakci.movieapp.dao.CategoryRepository;
import com.yildirimbayrakci.movieapp.dao.MovieRepository;
import com.yildirimbayrakci.movieapp.entity.Actor;
import com.yildirimbayrakci.movieapp.entity.Category;
import com.yildirimbayrakci.movieapp.entity.Movie;

@Service
public class MovieServiceImpl implements MovieService {

	private MovieRepository movieRepository;
	private ActorRepository actorRepository;
	private CategoryRepository categoryRepository;

	@Autowired
	public MovieServiceImpl(MovieRepository movieRepository, ActorRepository actorRepository,
			CategoryRepository categoryRepository) {
		// Constructor Injection
		this.movieRepository = movieRepository;
		this.actorRepository = actorRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Movie> findAllMovies() {
		return movieRepository.findAll();
	}

	@Override
	public Page<Movie> findAllMovies(Pageable pageable) {
		return movieRepository.findAll(pageable);
	}

	@Override
	public Movie findMovieById(int movieId) {
		Optional<Movie> result = movieRepository.findById(movieId);
		Movie movie = null;
		if (result.isPresent())
			movie = result.get();
		else
			throw new RuntimeException("Film bulunamadı. ID: " + movieId);
		return movie;
	}

	@Override
	public Movie saveMovie(Movie movie) {
		Movie savedMovie = movieRepository.save(movie);
		return savedMovie;
	}

	@Override
	public List<Category> findAllCategories() {
		return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

	@Override
	public Category findCategoryById(int categoryId) {
		Optional<Category> result = categoryRepository.findById(categoryId);
		Category category = null;
		if (result.isPresent())
			category = result.get();
		else
			throw new RuntimeException("Kategori bulunamadı. ID: " + categoryId);
		return category;
	}

	@Override
	public List<Actor> findAllActors() {
		return actorRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

	@Override
	public Actor findActorById(int actorId) {
		Optional<Actor> result = actorRepository.findById(actorId);
		Actor actor = null;
		if (result.isPresent())
			actor = result.get();
		else
			throw new RuntimeException("Oyuncu bulunamadı. ID: " + actorId);

		return actor;
	}

	@Override
	public void deleteMovieById(int id) {
		movieRepository.deleteById(id);
	}

	@Override
	public Page<Movie> findMovieByTitleContaining(String movieTitle, Pageable pageable) {
		return movieRepository.findByTitleContaining(movieTitle, pageable);
	}

	@Override
	public void saveActor(Actor actor) {
		actorRepository.save(actor);
	}

	@Override
	public void saveCategory(Category category) {
		categoryRepository.save(category);
	}

	@Override
	public Actor findActorByName(String name) {
		return actorRepository.findByName(name);
	}


	@Override
	public Page<Movie> findMoviesByCategoryAndTitleContaining(int categoryId, String title, Pageable pageable) {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(categoryId);
		return movieRepository.findByCategories_IdInAndTitleContaining(ids, title, pageable);
	}

	@Override
	public Page<Movie> findMoviesByCategoryAndActorName(int categoryId, String query, Pageable pageable) {
		// get actors
		List<Actor> actorsFound = actorRepository.findByNameContaining(query);
		if (actorsFound == null || !categoryRepository.findById(categoryId).isPresent()) {
			// no actor or category found. return.
			return null;
		} else {
			// get the movies from categories and actors
			List<Integer> ids = new ArrayList<Integer>();
			ids.add(categoryId);
			return movieRepository.findDistinctByCategories_IdInAndActorsIn(ids, actorsFound, pageable);
		}
	}

	@Override
	public Page<Movie> findMoviesByActorName(String query, Pageable pageable) {
		// get actors
		List<Actor> actorsFound = actorRepository.findByNameContaining(query);
		if (actorsFound == null) {
			// no actor found. return.
			return null;
		} else {
			// get the movies of found actors.
			return movieRepository.findDistinctByActorsIn(actorsFound, pageable);
		}
	}

	@Override
	public Page<Movie> findMoviesByUserFavoritedBy(Long userId, Pageable pageable) {
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.add(userId);
		return movieRepository.findByUsersFavoritedBy_IdIn(userIdList, pageable);
	}

	@Override
	public void deleteCategory(int categoryId) {
		categoryRepository.deleteById(categoryId);
	}

	@Override
	public Set<Movie> findMovieByCategory(int categoryId) {
		List<Integer> categoryIds = new ArrayList<Integer>();
		categoryIds.add(categoryId);
		return movieRepository.findByCategories_IdIn(categoryIds);
	}
	
}
