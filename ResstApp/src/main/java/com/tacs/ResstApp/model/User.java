package com.tacs.ResstApp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {

	private Long id;

	private String username;

	private List<Repository> favourites;
	
	private LocalDateTime lastLoginDate;
	
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
		return this.languages;
	}
	
	public void setLanguages(List<String> langs) {
		this.languages = langs;
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
	
	
}
