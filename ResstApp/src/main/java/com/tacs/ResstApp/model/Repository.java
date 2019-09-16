package com.tacs.ResstApp.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Repository {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Transient
	private String name;

	@Transient
	private LocalDate registrationDate;

	@Transient
	private int noffaved;

	@Transient
	private List<String> languages;

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

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public int getNoffaved() {
		return noffaved;
	}

	public void setNoffaved(int noffaved) {
		this.noffaved = noffaved;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
}
