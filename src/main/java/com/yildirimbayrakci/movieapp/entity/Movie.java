package com.yildirimbayrakci.movieapp.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "movie")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title", nullable = false)
	@NotEmpty(message = "Boş olamaz")
	private String title;

	@Column(name = "director")
	private String director;

	@Column(name = "description")
	private String description;

	@Column(name = "picture_url")
	private String pictureUrl;

	@Column(name = "release_year")
	private short releaseYear;

	@Column(name = "language")
	private String language;

	@Column(name = "trailer_url")
	private String trailerUrl;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "movie_actor", joinColumns = { @JoinColumn(name = "movie_id") }, inverseJoinColumns = {
			@JoinColumn(name = "actor_id") })
	private Set<Actor> actors;

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "movie_category", joinColumns = { @JoinColumn(name = "movie_id") }, inverseJoinColumns = {
			@JoinColumn(name = "category_id") })
	private Set<Category> categories;
	
	@ManyToMany(mappedBy = "favoriteMovies")
	private Set<User> usersFavoritedBy;
	
	@OneToOne
	@JoinColumn(name = "added_by_user")
	private User addedBy;
	

	public Movie() {
	}

	public Movie(String title, String description, String pictureUrl, short releaseYear, String language,
			String director, String trailerUrl) {
		this.title = title;
		this.director = director;
		this.description = description;
		this.pictureUrl = pictureUrl;
		this.releaseYear = releaseYear;
		this.language = language;
		this.trailerUrl = trailerUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public short getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(short releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	

	public User getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}

	public Set<Actor> getActors() {
		return actors;
	}

	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public String getTrailerUrl() {
		return trailerUrl;
	}

	public void setTrailerUrl(String trailerUrl) {
		this.trailerUrl = trailerUrl;
	}

	public Set<User> getUsersFavoritedBy() {
		return usersFavoritedBy;
	}

	public void setUsersFavoritedBy(Set<User> usersFavoritedBy) {
		this.usersFavoritedBy = usersFavoritedBy;
	}
	
	public void addCategory(Category category) {
		categories.add(category);
		category.getMovies().add(this);
	}

	public void removeCategory(Category category) {
		categories.remove(category);
		category.getMovies().remove(this);
	}
}
