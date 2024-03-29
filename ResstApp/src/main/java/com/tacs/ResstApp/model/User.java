package com.tacs.ResstApp.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	private boolean admin;
	
	@ManyToMany
	private List<Repository> favourites = new ArrayList<>();
	
	private LocalDateTime lastLoginDate;

	@ElementCollection
	private List<String> languages;


	public User(){
		favourites = new ArrayList<>();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public LocalDateTime getLastLoginDate() {
		return lastLoginDate;
	}
	
	public void setLastLoginDate(LocalDateTime date) {
		this.lastLoginDate = date;
	}
	
	public List<String> getLanguages() {
		return calculateFavouriteLanguages();
	}
	
	public void setLanguages(List<String> langs) {
		this.languages = langs;
	}
	
	public void addFavourite(Repository repo) {
		this.favourites.add(repo);
	}
	
	public void deleteFavourite(Repository repo) {
		this.favourites.removeIf(f -> f.getId().equals(repo.getId()));
	}

	public List<Repository> getFavourites() {
		return favourites;
	}

	public void setFavourites(List<Repository> favourites) {
		this.favourites = favourites;
	}

	public int getNofFavourites() {
		return this.favourites.size();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isAdmin() {
		return admin;
	}


	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public List<String> calculateFavouriteLanguages() {
		return this.favourites.stream().map(f -> f.getMainLanguage()).distinct().collect(Collectors.toList());
	}
	
}
