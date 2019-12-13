package com.yildirimbayrakci.movieapp.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yildirimbayrakci.movieapp.entity.Category;
import com.yildirimbayrakci.movieapp.entity.Movie;
import com.yildirimbayrakci.movieapp.service.MovieService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	private MovieService movieService;

	@Autowired
	public CategoryController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping("/list")
	public String showList(Model model) {
		List<Category> categories = movieService.findAllCategories();
		model.addAttribute("allCategories", categories);
		
		Category category = new Category();
		model.addAttribute("category", category);
		return "category-list";
	}
	
	@GetMapping("/delete")
	public String deleteCategory(@RequestParam("categoryId") int categoryId) {
		// delete category and redirect to category list
		
		Set<Movie> movies = movieService.findMovieByCategory(categoryId);
		Category c = movieService.findCategoryById(categoryId);
		
		for(Movie m: movies) {
			m.removeCategory(c);
		}

		movieService.deleteCategory(categoryId);
		return "redirect:/categories/list";
	}
	
	@GetMapping("/edit")
	public String editCategory(@RequestParam("categoryId") int categoryId, Model model) {
		
		List<Category> categories = movieService.findAllCategories();
		model.addAttribute("allCategories", categories);
		Category category = movieService.findCategoryById(categoryId);
		model.addAttribute("category", category);
		return "category-form";
	}

	@GetMapping("/addCategoryForm")
	public String showAddCategoryForm(Model model) {
		List<Category> categories = movieService.findAllCategories();
		model.addAttribute("allCategories", categories);
		Category category = new Category();
		model.addAttribute("category", category);

		return "category-form";
	}

	@PostMapping("/save")
	public String processForm(@ModelAttribute("category") Category category) {
		movieService.saveCategory(category);
		return "redirect:/categories/list";
	}
}
