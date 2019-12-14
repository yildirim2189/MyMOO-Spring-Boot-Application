package com.yildirimbayrakci.movieapp.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yildirimbayrakci.movieapp.entity.Category;
import com.yildirimbayrakci.movieapp.entity.Movie;
import com.yildirimbayrakci.movieapp.entity.User;
import com.yildirimbayrakci.movieapp.service.MovieService;
import com.yildirimbayrakci.movieapp.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private MovieService movieService;
	private UserService userService;
	private static final int PAGE = 0;
	// Amount of entity(movie) to show in one page
	private static final int PAGE_SIZE = 20;
	
	@Autowired
	public UserController(MovieService movieService, UserService userService) {
		this.movieService = movieService;
		this.userService = userService;
	}

	@GetMapping("/profile")
	public String showProfile(HttpServletRequest request, Model model, Principal principal) {

		User user = userService.findUserByUsername(principal.getName());

		int page = PAGE;
		int size = PAGE_SIZE;

		if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
			page = Integer.parseInt(request.getParameter("page")) - 1;
		}

		if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
			page = Integer.parseInt(request.getParameter("size"));
		}

		Page<Movie> movies = movieService.findMoviesByUserFavoritedBy(user.getId(), PageRequest.of(page, size));

		model.addAttribute("movies", movies);
		List<Category> categories = movieService.findAllCategories();
		model.addAttribute("allCategories", categories);
		
		if(user.getType().equals("google"))
			user.setUsername("Google Kullanıcısı");
		model.addAttribute("user",user);

		return "profile";
	}
}
