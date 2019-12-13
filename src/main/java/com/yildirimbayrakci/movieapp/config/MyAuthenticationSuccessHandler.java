package com.yildirimbayrakci.movieapp.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.yildirimbayrakci.movieapp.entity.User;
import com.yildirimbayrakci.movieapp.service.UserService;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	UserService userService;

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		
		// Get User.
		String username = auth.getName();
		User user = userService.findUserByUsername(username);
		
		// Place in session.
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		// Redirect to Home Page.
		response.sendRedirect(request.getContextPath() + "/");
	}
}
