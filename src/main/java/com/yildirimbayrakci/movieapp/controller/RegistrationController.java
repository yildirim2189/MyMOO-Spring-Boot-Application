package com.yildirimbayrakci.movieapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yildirimbayrakci.movieapp.entity.User;
import com.yildirimbayrakci.movieapp.service.UserService;
import com.yildirimbayrakci.movieapp.validation.AppUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	UserService userService;

	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/showRegistrationForm")
	public String showRegistration(Model model) {
		model.addAttribute("appUser", new AppUser());
		return "registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("appUser") AppUser appUser,
			BindingResult bindingResult, Model model) {

		String username = appUser.getUsername();
		System.out.println("Registration processing for username: " + username);

		if (bindingResult.hasErrors())
			return "registration-form";

		User existing = userService.findUserByUsername(username);
		if (existing != null) {
			model.addAttribute("appUser", new AppUser());
			model.addAttribute("registrationError", "Kullanıcı adı zaten kayıtlı.");
			return "registration-form";
		}
		
		existing = userService.findByEmail(appUser.getEmail());
		if (existing != null && existing.getType().equals("normal")) {
			model.addAttribute("appUser", new AppUser());
			model.addAttribute("registrationError", "Email adresi zaten kayıtlı.");
			return "registration-form";
		}
		

		// Create user account
		userService.saveUser(appUser);
		System.out.println("User successfully created: " + username);
		return "registration-confirmation";
	}
}
