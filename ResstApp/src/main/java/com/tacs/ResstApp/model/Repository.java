package com.tacs.ResstApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Repository {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private LocalDate registrationDate;
	
	private int noffaved;
	
	private int totalCommits;
	
	private String mainLanguage;
	
	private Integer totalIssues;
	
	private Double score;

	public Repository(Long id, String name) {
		this.id = id;
		this.name = name;
		this.registrationDate = LocalDate.now();
	}

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
	
	public int getNofFaved() { 
		return noffaved;
	}
	
	public void setNofFaved(int n) {
		noffaved = n;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public int getTotalCommits() {
		return totalCommits;
	}

	public void setTotalCommits(int totalCommits) {
		this.totalCommits = totalCommits;
	}

	public String getMainLanguage() {
		return mainLanguage;
	}

	public void setMainLanguage(String mainLanguage) {
		this.mainLanguage = mainLanguage;
	}

	public Integer getTotalIssues() {
		return totalIssues;
	}

	public void setTotalIssues(Integer totalIssues) {
		this.totalIssues = totalIssues;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}
