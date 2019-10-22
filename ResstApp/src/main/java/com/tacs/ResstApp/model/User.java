package com.tacs.ResstApp.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	private boolean admin;
	
	@ManyToMany
	private List<Repository> favourites;
	
	private LocalDateTime lastLoginDate;

	@ElementCollection
	private List<String> languages;


	public User(){
		favourites = new ArrayList<>();
	}

	public void addFavourite(Repository repo) {
		this.favourites.add(repo);
	}
	
	public void deleteFavourite(Repository repo) {
		this.favourites.removeIf(f -> f.getId().equals(repo.getId()));
	}

	public int getNofFavourites() {
		return this.favourites.size();
	}

	public List<String> calculateFavouriteLanguages() {
		return this.favourites.stream().map(f -> f.getMainLanguage()).distinct().collect(Collectors.toList());
	}
	
}
