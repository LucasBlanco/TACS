package com.tacs.ResstApp.model;

import java.util.ArrayList;
import java.util.List;

public class User {

	private Long id;

	private String username;

	private List<Repository> favourites;

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

	public List<Repository> getFavourites() {
		return favourites;
	}

	public void setFavourites(List<Repository> favourites) {
		this.favourites = favourites;
	}
}
