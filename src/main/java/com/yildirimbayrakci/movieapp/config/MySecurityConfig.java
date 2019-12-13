package com.yildirimbayrakci.movieapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.yildirimbayrakci.movieapp.service.CustomOidcUserService;
import com.yildirimbayrakci.movieapp.service.UserService;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	 
	@Autowired
	private CustomOidcUserService oidcUserService;

	@Autowired
	private MyAuthenticationSuccessHandler authSuccessHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/movies/search*").permitAll()
		.antMatchers("/user/**").hasAnyRole("USER","ADMIN")
		.antMatchers("/movies/addMovie*").hasRole("USER")
		.antMatchers("/movies/edit*").hasRole("ADMIN")
		.antMatchers("/movies/delete*").hasRole("ADMIN")
		.antMatchers("/movies/addToFavorites*").hasRole("USER")
		.antMatchers("/movies/removeFromFavorites*").hasRole("USER")
		.antMatchers("/movies/edit*").hasRole("USER")
		.antMatchers("/actors/**").hasRole("USER")
		.antMatchers("/categories/**").hasRole("ADMIN")
		.antMatchers("/actuator/**").hasRole("ADMIN")
		.and()
		.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/authenticateTheUser")
			.successHandler(authSuccessHandler)
			.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/access-denied");
		
		// oAauth2 Login
		http.oauth2Login().loginPage("/login").successHandler(authSuccessHandler).userInfoEndpoint().oidcUserService(oidcUserService);
	}
	
	// BCrypt Bean.
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Authentication Provider
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
		// set the custom user details service
		dap.setUserDetailsService(userService); 
		// set the password encoder - bcrypt
		dap.setPasswordEncoder(passwordEncoder()); 
		// return auth provider
		return dap;
	}
}
