package com.yildirimbayrakci.movieapp.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yildirimbayrakci.movieapp.entity.Actor;
import com.yildirimbayrakci.movieapp.entity.Category;
import com.yildirimbayrakci.movieapp.entity.Movie;
import com.yildirimbayrakci.movieapp.entity.Role;
import com.yildirimbayrakci.movieapp.entity.User;
import com.yildirimbayrakci.movieapp.service.MovieService;
import com.yildirimbayrakci.movieapp.service.UserService;

@Controller
@RequestMapping("/movies")
public class MovieController {

	private MovieService movieService;
	private UserService userService;
	private static final int PAGE = 0;
	private static final int PAGE_SIZE = 50;

	@Autowired
	public MovieController(MovieService movieService, UserService userService) {
		// Constructor injection
		this.movieService = movieService;
		this.userService = userService;
	}

	// Show empty form for add movie.
	@GetMapping("/addMovieForm")
	public String showAddMovieForm(Model model) {

		// Add new movie to model
		Movie movie = new Movie();
		model.addAttribute("movie", movie);
		// Add actor to model
		Actor actor = new Actor();
		model.addAttribute("actor", actor);

		// Add categories and actors to populate select input areas
		List<Category> categories = movieService.findAllCategories();
		List<Actor> actors = movieService.findAllActors();
		model.addAttribute("allCategories", categories);
		model.addAttribute("allActors", actors);
		return "movie-form";
	}

	// Show populated form for edit the movie
	@GetMapping("/editMovieForm")
	public String showEditMovieForm(@RequestParam("movieId") int movieId, Model model, Principal principal) {
		
		// Find the movie and add to model to populate form
		Movie movie = movieService.findMovieById(movieId);
		model.addAttribute("movie", movie);

		if (principal != null) {
			User user = userService.findUserByUsername(principal.getName());
			Role role = userService.findRoleByName("ROLE_ADMIN");
			// if user is not admin and movie was not added by that user
			if (!user.getRoles().contains(role) && user.getId() != movie.getAddedBy().getId()) {
				// return access denied
				return "access-denied";
			}
		}

		// Get movie categories and all categories and add to model
		List<Category> categories = movieService.findAllCategories();
		model.addAttribute("allCategories", categories);
		model.addAttribute("includedCategories", movie.getCategories());
		// Get movie actors and all actors and add to model
		List<Actor> actors = movieService.findAllActors();
		model.addAttribute("allActors", actors);
		model.addAttribute("includedActors", movie.getActors());
		return "movie-form";
	}

	// Add movie to user's favorite movies.
	@GetMapping("/addToFavorites")
	public String addMovieToFavorites(@RequestParam("movieId") int movieId, Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/showMyLoginPage";
		} else {
			// if user exists get movie and user objects
			User user = userService.findUserByUsername(principal.getName());
			Movie movie = movieService.findMovieById(movieId);
			// Save movie for user
			userService.saveUserFavoriteMovie(user, movie);
			// redirect to the movie detail page
			return "redirect:/movies/details?movieId=" + movieId;
		}
	}

	// Remove movie from user's favorite movies
	@GetMapping("/removeFromFavorites")
	public String removeMovieFromFavorites(@RequestParam("movieId") int movieId, Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/showMyLoginPage";
		} else {
			// if user exists get movie and user objects
			User user = userService.findUserByUsername(principal.getName());
			Movie movie = movieService.findMovieById(movieId);
			// remove movie from favorites
			userService.removeUserFavoriteMovie(user, movie);

			return "redirect:/movies/details?movieId=" + movieId;
		}
	}

	// Show movie details
	@GetMapping("/details")
	public String showDetails(@RequestParam("movieId") int movieId, Model model, Principal principal) {
		// get movie object, categories, actors and add to model
		Movie movie = movieService.findMovieById(movieId);
		model.addAttribute("movie", movie);
		model.addAttribute("movieCategories", movie.getCategories());
		model.addAttribute("movieActors", movie.getActors());

		// Add all categories to model to populate navbar links
		List<Category> categories = movieService.findAllCategories();
		model.addAttribute("allCategories", categories);

		long userId = 0;
		// Check if movie is favorite or not, to show correct button to user and add to
		// model
		boolean isMovieFavorite = false;
		if (principal != null) {
			User user = userService.findUserByUsername(principal.getName());
			isMovieFavorite = user.getFavoriteMovies().contains(movie);
			userId = user.getId();
		}
		model.addAttribute("isMovieFavorite", isMovieFavorite);
		model.addAttribute("userId", userId);

		return "movie-details";
	}

	// Save(Create/Update) Movie
	@PostMapping("/save")
	public String processForm(@ModelAttribute("movie") Movie movie,
			@RequestParam(value = "categoryIds", required = false) int[] categoryIds,
			@RequestParam(value = "actorIds", required = false) int[] actorIds, Principal principal) {

		// if actors added to movie, set actors.
		if (actorIds != null) {
			Set<Actor> actors = new HashSet<Actor>();
			for (int id : actorIds) {
				Actor actor = movieService.findActorById(id);
				actors.add(actor);
			}
			movie.setActors(actors);
		}

		// if categories added to movie, set categories
		if (categoryIds != null) {
			Set<Category> categories = new HashSet<Category>();
			for (int id : categoryIds) {
				Category category = movieService.findCategoryById(id);
				categories.add(category);
			}
			movie.setCategories(categories);
		}
		// Convert to normal Youtube URL to embed URL
		movie.setTrailerUrl(convertToYoutubeEmbedUrl(movie.getTrailerUrl()));
		// Set user who added movie if there's no.
		if (movie.getId() == 0)
			movie.setAddedBy(userService.findUserByUsername(principal.getName()));
		else
			movie.setAddedBy(movieService.findMovieById(movie.getId()).getAddedBy());
		// Save movie
		Movie savedMovie = movieService.saveMovie(movie);
		// Redirect to movie detail page.
		return "redirect:/movies/details?movieId=" + savedMovie.getId();
	}

	@GetMapping("/delete")
	public String deleteMovie(@RequestParam("movieId") int id) {
		// Delete movie and redirect to home page
		movieService.deleteMovieById(id);
		return "redirect:/movies/search";
	}

	@GetMapping("/search")
	public String searchMovie(HttpServletRequest request,
			@RequestParam(name = "query", required = false, defaultValue = "") String query,
			@RequestParam(name = "sort", required = false, defaultValue = "Id") String sort,
			@RequestParam(name = "category", required = false) Integer categoryId,
			@RequestParam(name = "searchBy", required = false, defaultValue = "title") String searchBy, Model model,
			Principal principal) {

		// Get categories to populate navbar links
		List<Category> categories = movieService.findAllCategories();
		model.addAttribute("allCategories", categories);

		// Generate attributes for pagination
		int page = PAGE;
		int size = PAGE_SIZE;

		if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
			page = Integer.parseInt(request.getParameter("page")) - 1;
		}
		if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
			page = Integer.parseInt(request.getParameter("size"));
		}

		Page<Movie> movies = null;
		String categoryName = null;

		/* GET SEARCH RESULT */
		// Search in all categories
		if (categoryId == null) {
			// Search By Title
			if (searchBy.equals("title")) {
				movies = movieService.findMovieByTitleContaining(query,
						PageRequest.of(page, size, Sort.Direction.ASC, sort));
				// Search By Actor Name
			} else if (searchBy.equals("actorName")) {
				movies = movieService.findMoviesByActorName(query,
						PageRequest.of(page, size, Sort.Direction.ASC, sort));
			} else
				; // Another options not supported yet.
		}
		// Search in one category
		else {
			// Search By Title
			if (searchBy.equals("title")) {
				movies = movieService.findMoviesByCategoryAndTitleContaining(categoryId, query,
						PageRequest.of(page, size, Sort.Direction.ASC, sort));
			}
			// Search By Actor Name
			else {
				movies = movieService.findMoviesByCategoryAndActorName(categoryId, query,
						PageRequest.of(page, size, Sort.Direction.ASC, sort));
			}
			// Get the category name we're searching in.
			categoryName = movieService.findCategoryById(categoryId).getName();
		}

		// if no result, add attribute for show "No Result" text.
		if (movies.isEmpty())
			model.addAttribute("emptyResult", true);

		// Add movie objects and save user inputs.
		model.addAttribute("movies", movies);
		model.addAttribute("query", query);
		model.addAttribute("sort", sort);
		model.addAttribute("category", categoryId);
		model.addAttribute("searchBy", searchBy);

		// Add category's name to model, for show in search bar.
		if (categoryName == null)
			categoryName = "Tümü";
		model.addAttribute("cName", categoryName);
		return "movie-list";
	}

	public String convertToYoutubeEmbedUrl(String normalUrl) {
		// if url exist, converts format of url to Youtube embed URL.
		String embedUrl = "";
		if (normalUrl.contains("www.youtube.com/embed/"))
			return normalUrl;
		if (normalUrl != null && !normalUrl.isBlank() && !normalUrl.isEmpty()) {
			if (normalUrl.contains("www.youtube.com/watch?v="))
				embedUrl = "https://www.youtube.com/embed/"
						+ normalUrl.substring(normalUrl.lastIndexOf("watch?v=") + 8);
			else if (normalUrl.contains("youtu.be/"))
				embedUrl = "https://www.youtube.com/embed/"
						+ normalUrl.substring(normalUrl.lastIndexOf("youtu.be/") + 9);
		}
		return embedUrl;
	}
}
