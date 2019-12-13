package com.yildirimbayrakci.movieapp.validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = "Parola alanları eşleşmiyor")
})
public class AppUser {

	@NotNull(message = "gerekli")
	@Size(min = 1, message = "gerekli")
	private String username;
	
	@NotNull(message = "gerekli")
	@Size(min = 6, message = "Parolanız 6 karakterden az olamaz")
	private String password;
	
	@NotNull(message = "gerekli")
	@Size(min = 1, message = "Parolanız 6 karakterden az olamaz")
	private String matchingPassword;
	
	@NotNull(message = "gerekli")
	@Size(min = 1, message = "gerekli")
	private String firstName;
	
	@NotNull(message = "gerekli")
	@Size(min = 1, message = "gerekli")
	private String lastName;
	
	@ValidEmail
	@NotNull(message = "gerekli")
	@Size(min = 1, message = "gerekli")
	private String email;
	
	public AppUser() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
