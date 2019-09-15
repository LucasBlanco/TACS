package com.tacs.ResstApp.model;

import java.time.LocalDate;

public class Repository {

	private Long id;

	private String name;

	private LocalDate registrationDate;
	
	private int noffaved;

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

}
