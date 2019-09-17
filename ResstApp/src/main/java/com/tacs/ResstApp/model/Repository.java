package com.tacs.ResstApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Repository {

	@Id
	private Long id;

	@Transient
	private String name;

	@Transient
	private LocalDate registrationDate;

	@Transient
	private int nofFaved;
	
	private int totalCommits;
	
	private String mainLanguage;
	
	private Integer totalIssues;
		
	private Double score;
	
	private Integer nofForks;

	@Transient
	private List<String> languages;

	public Repository(Long id, String name) {
		this.id = id;
		this.name = name;
		this.registrationDate = LocalDate.now();
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

	public int getTotalCommits() {
		return totalCommits;
	}

	@JsonProperty("size")
	public void setTotalCommits(int totalCommits) {
		this.totalCommits = totalCommits;
	}

	public String getMainLanguage() {
		return mainLanguage;
	}

	@JsonProperty("language")
	public void setMainLanguage(String mainLanguage) {
		this.mainLanguage = mainLanguage;
	}

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

	public int getNofFaved() {
		return nofFaved;
	}

	@JsonProperty("stargazers_count")
	public void setNofFaved(int noffaved) {
		this.nofFaved = noffaved;
	}

	public Integer getNofForks() {
		return nofForks;
	}

	@JsonProperty("forks_count")
	public void setNofForks(Integer nofForks) {
		this.nofForks = nofForks;
	}

	
}
