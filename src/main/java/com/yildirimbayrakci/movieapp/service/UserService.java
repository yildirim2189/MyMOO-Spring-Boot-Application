package com.yildirimbayrakci.movieapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.yildirimbayrakci.movieapp.entity.Movie;
import com.yildirimbayrakci.movieapp.entity.Role;
import com.yildirimbayrakci.movieapp.entity.User;
import com.yildirimbayrakci.movieapp.validation.AppUser;

public interface UserService extends UserDetailsService {
	
	public User findUserByUsername(String username);
	
	public void saveUser(AppUser appUser);
	
	public void saveUserFavoriteMovie(User user, Movie movie);
	
	public void removeUserFavoriteMovie(User user, Movie movie);

	public User findByEmail(String email);
	
	public Role findRoleByName(String name);

	
}
