package com.yildirimbayrakci.movieapp.service;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yildirimbayrakci.movieapp.dao.RoleRepository;
import com.yildirimbayrakci.movieapp.dao.UserRepository;
import com.yildirimbayrakci.movieapp.entity.User;

@Service
public class CustomOidcUserService extends OidcUserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	
	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = super.loadUser(userRequest);
		// Get attributes
		Map<String, Object> attributes = oidcUser.getAttributes();
		String email = (String)attributes.get("email");
		String id = (String)attributes.get("sub");
		String firstName = (String)attributes.get("given_name");
		String lastName =(String)attributes.get("family_name");
		updateUser(email,id,firstName, lastName);
		
		System.out.println(oidcUser.toString());
		return oidcUser;
		
	}
	
	@Transactional
	private void updateUser(String email, String id, String firstName, String lastName) {
		
		Optional<User> u = userRepository.findByEmailAndType(email, "google");
		User user;
	
		// if google user exist get user
		if(u.isPresent()) {
			user = u.get();
		} 
		else {
			// create new user
			user = new User();
		}
			
		// update google user
		user.setUsername(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setType("google");
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER").get()));
		userRepository.save(user);
	}
}
