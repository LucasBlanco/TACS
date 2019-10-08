package com.tacs.ResstApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Repository {

	@Id
	private Long id;

	private String name;

	private LocalDate registrationDate;

	private int stars;
	
	private String owner;

	private int favs;
	
	private String mainLanguage;
	
	private String languagesUrl;
	
	private Integer totalIssues;
		
	private Double score;
	
	private Integer nofForks;
	
	private Integer size;

	@ElementCollection
	private List<String> languages;

	public Repository(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Repository() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("registrationDate")
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	@JsonProperty("created_at")
	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public String getMainLanguage() {
		return mainLanguage;
	}

	@JsonProperty("language")
	public void setMainLanguage(String mainLanguage) {
		this.mainLanguage = mainLanguage;
	}
	
	
	public String getOwner() {
		return owner;
	}

	@JsonProperty("owner")
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getLanguagesUrl() {
		return languagesUrl;
	}

	public void setLanguagesUrl(String languagesUrl) {
		this.languagesUrl = languagesUrl;
	}
	

	@JsonProperty("issues")
	public Integer getTotalIssues() {
		return totalIssues;
	}

	@JsonProperty("open_issues_count")
	public void setTotalIssues(Integer totalIssues) {
		this.totalIssues = totalIssues;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@JsonProperty("stars")
	public int getStars() {
		return stars;
	}

	@JsonProperty("stargazers_count")
	public void setStars(int stars) {
		this.stars = stars;
	}

	@JsonProperty("forks")
	public Integer getNofForks() {
		return nofForks;
	}

	@JsonProperty("forks_count")
	public void setNofForks(Integer nofForks) {
		this.nofForks = nofForks;
	}

	@JsonProperty("favs")
	public int getFavs() {
		return favs;
	}

	public void setFavs(int favs) {
		this.favs = favs;
	}
	
	public void favved() {
		this.favs++;
	}
	
	public void unfavved() {
		this.favs--;
	}

	public Integer getSize() {
		return size;
	}

	@JsonProperty("size")
	public void setSize(Integer size) {
		this.size = size;
	}
}
