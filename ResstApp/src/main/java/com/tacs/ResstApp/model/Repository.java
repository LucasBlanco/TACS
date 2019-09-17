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

	public Repository(Long id, String name) {
		this.id = id;
		this.name = name;
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

	public Repository setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
		return this;
	}

}
