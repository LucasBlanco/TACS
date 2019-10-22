package com.tacs.ResstApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Repository {

	@Id
	private Long id;

	private String name;

	private LocalDate registrationDate;

	private int stars;

	@JsonProperty("owner")
	private String owner;

	@JsonProperty("favs")
	private int favs;

	@JsonProperty("language")
	private String mainLanguage;

	private String languagesUrl;

	private Integer totalIssues;
		
	private Double score;
	
	private Integer nofForks;

	@JsonProperty("size")
	private Integer size;

	@ElementCollection
	private List<String> languages;

	public Repository(Long id, String name) {
		this.id = id;
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

	@JsonProperty("issues")
	public Integer getTotalIssues() {
		return totalIssues;
	}

	@JsonProperty("open_issues_count")
	public void setTotalIssues(Integer totalIssues) {
		this.totalIssues = totalIssues;
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

	public void favved() {
		this.favs++;
	}
	
	public void unfavved() {
		this.favs--;
	}

}
