package com.yildirimbayrakci.movieapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yildirimbayrakci.movieapp.entity.Actor;
import com.yildirimbayrakci.movieapp.entity.Category;
import com.yildirimbayrakci.movieapp.service.MovieService;

@Controller
@RequestMapping("/actors")
public class ActorController {

	private MovieService movieService;

	@Autowired
	public ActorController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping("/list")
	public String showList(Model model) {
		List<Category> categories = movieService.findAllCategories();
		model.addAttribute("allCategories", categories);
		List<Actor> actors = movieService.findAllActors();
		model.addAttribute("actors", actors);
		return "actor-list";
	}

	@GetMapping("/addActorForm")
	public String showAddActorForm(Model model) {
		Actor actor = new Actor();
		model.addAttribute("actor", actor);
		
		List<Category> categories = movieService.findAllCategories();
		model.addAttribute("allCategories", categories);

		return "actor-form";
	}

	@PostMapping("/save")
	public String processForm(@ModelAttribute("actor") Actor actor, Model model) {
		if(movieService.findActorByName(actor.getName()) != null) {

			model.addAttribute("isActorExist", true);
		
			return showAddActorForm(model);
		}
		movieService.saveActor(actor);
		return showAddActorForm(model);
	}
}
