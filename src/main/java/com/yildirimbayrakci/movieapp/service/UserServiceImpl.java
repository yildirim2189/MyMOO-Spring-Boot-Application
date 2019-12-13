package com.yildirimbayrakci.movieapp.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yildirimbayrakci.movieapp.dao.RoleRepository;
import com.yildirimbayrakci.movieapp.dao.UserRepository;
import com.yildirimbayrakci.movieapp.entity.Movie;
import com.yildirimbayrakci.movieapp.entity.Role;
import com.yildirimbayrakci.movieapp.entity.User;
import com.yildirimbayrakci.movieapp.validation.AppUser;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User findUserByUsername(String username) {
		Optional<User> result = userRepository.findByUsername(username);
		User user = null;
		if(result.isPresent())
			user = result.get();
		else
			; // Throw exception
				
		return user;
	}

	@Override
	public void saveUser(AppUser appUser) {
		User user = new User();
		
		user.setUsername(appUser.getUsername());
		user.setPassword(passwordEncoder.encode(appUser.getPassword()));
		user.setFirstName(appUser.getFirstName());
		user.setLastName(appUser.getLastName());
		user.setEmail(appUser.getEmail());
		user.setType("normal");
		
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER").get()));
		userRepository.save(user);
	}
	
	@Override
	public void saveUserFavoriteMovie(User user, Movie movie) {
		if(user.addMovieToFavorites(movie))
			System.out.println("movie added: " + movie.getTitle());
		else
			System.out.println("movie already in fav: " + movie.getTitle());
		userRepository.save(user);
	}
	
	@Override
	public void removeUserFavoriteMovie(User user, Movie movie) {
		if(user.removeMovieFromFavorite(movie))
			System.out.println("movie removed: " + movie.getTitle());
		else
			System.out.println("movie not present in favorites: " + movie.getTitle());
		userRepository.save(user);
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> result = userRepository.findByUsername(username);
		
		if(!result.isPresent())
			throw new UsernameNotFoundException("Geçersiz kullanıcı adı veya parola.");
		
		User user = result.get();
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword()
				, mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public User findByEmail(String email) {
		Optional<User> u = userRepository.findByEmail(email);
		if(u.isPresent())
			return u.get();
		return null;
	}

	@Override
	public Role findRoleByName(String name) {
		Role role = null;
		Optional<Role> roleOpt = roleRepository.findByName(name);
		if(roleOpt.isPresent())
			role = roleOpt.get();
		return role;
		
	}
}
